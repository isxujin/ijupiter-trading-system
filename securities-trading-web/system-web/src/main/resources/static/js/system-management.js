/* Common list/form helpers for system management pages
   - initListPage(options)
     options: { endpoint, tableId, paginationId, searchFormId, columns, idField='id', basePath }
*/
(function(){
    function serializeForm(form) {
        const fd = new FormData(form);
        const params = {};
        for (const [k,v] of fd.entries()) {
            if (v !== null && v !== '') params[k] = v;
        }
        return params;
    }

    function fetchJson(url) {
        return fetch(url, { credentials: 'same-origin' }).then(r => r.json());
    }

    function renderTableRows(tableId, items, columns, idField, basePath){
        const tbody = document.querySelector('#' + tableId + ' tbody');
        if (!tbody) return;
        tbody.innerHTML = '';
        if (!items || items.length === 0) {
            const colCount = (columns ? columns.length : 1) + 1;
            tbody.innerHTML = `<tr><td colspan="${colCount}" class="text-center">暂无数据</td></tr>`;
            return;
        }
        items.forEach(item => {
            const tr = document.createElement('tr');
            const cells = columns.map(col => `<td>${(item[col] !== undefined && item[col] !== null) ? item[col] : ''}</td>`).join('');
            const id = item[idField] || item.id || '';
            const actions = `
                <td class="table-actions">
                    <a class="btn btn-sm btn-primary" href="${basePath}/edit/${id}">编辑</a>
                    <a class="btn btn-sm btn-secondary" href="${basePath}/detail/${id}">查看</a>
                    <button class="btn btn-sm btn-danger" onclick="(function(){ if(confirm('确定删除？')){fetch('${basePath}/delete/'+${id}, {method:'DELETE'}).then(r=>r.json()).then(()=>location.reload());}})()">删除</button>
                </td>`;
            tr.innerHTML = cells + actions;
            tbody.appendChild(tr);
        });
    }

    function renderPagination(paginationId, pageInfo, onGo) {
        const pagination = document.getElementById(paginationId);
        if (!pagination) return;
        pagination.innerHTML = '';
        const pageNum = pageInfo.pageNum || 1;
        const pages = pageInfo.pages || 1;
        const total = pageInfo.total || 0;
        const hasPrevious = pageNum > 1;
        const hasNext = pageNum < pages;

        function createLi(label, disabled, handler, active) {
            const li = document.createElement('li');
            li.className = 'page-item' + (disabled ? ' disabled' : '') + (active ? ' active' : '');
            const a = document.createElement('a');
            a.className = 'page-link';
            a.href = '#';
            a.textContent = label;
            a.addEventListener('click', function(e){ e.preventDefault(); if(!disabled) handler(); });
            li.appendChild(a);
            return li;
        }

        pagination.appendChild(createLi('上一页', !hasPrevious, ()=>onGo(pageNum-1)));
        for (let i=1;i<=pages;i++) {
            pagination.appendChild(createLi(i, false, ()=>onGo(i), i===pageNum));
        }
        pagination.appendChild(createLi('下一页', !hasNext, ()=>onGo(pageNum+1)));

        const info = document.createElement('li');
        info.className = 'page-item disabled ms-3';
        info.innerHTML = `<a class="page-link" href="#">共 ${total} 条记录，第 ${pageNum}/${pages} 页</a>`;
        pagination.appendChild(info);
    }

    window.initListPage = function(options){
        const endpoint = options.endpoint;
        const tableId = options.tableId;
        const paginationId = options.paginationId || 'pagination';
        const searchFormId = options.searchFormId;
        const columns = options.columns || [];
        const idField = options.idField || 'id';
        const basePath = options.basePath || endpoint.replace(/\/data$/, '');

        let currentPage = 1;
        const pageSize = options.pageSize || 10;

        function load(){
            let params = new URLSearchParams({ pageNum: currentPage, pageSize: pageSize });
            if (searchFormId) {
                const form = document.getElementById(searchFormId);
                if (form) {
                    const extra = serializeForm(form);
                    for (const k in extra) params.append(k, extra[k]);
                }
            }
            const url = endpoint + '?' + params.toString();
            fetchJson(url).then(resp => {
                // support two shapes: {code:0, data: {...}} or {code:200, data: {...}} or raw page
                let body = resp;
                if (resp && typeof resp === 'object' && ('code' in resp)) {
                    body = resp.data || resp;
                }
                // try to find list array
                let items = body.list || body.data || body;
                // if page wrapper
                const pageInfo = {
                    pageNum: body.pageNum || body.page || (body.current || 1),
                    pages: body.pages || Math.ceil((body.total||0)/pageSize) || 1,
                    total: body.total || (Array.isArray(items)?items.length:0),
                };
                if (Array.isArray(items) && items.length && typeof items[0] === 'object') {
                    renderTableRows(tableId, items, columns, idField, basePath);
                } else if (Array.isArray(body)) {
                    renderTableRows(tableId, body, columns, idField, basePath);
                } else {
                    // fallback: if data is object with list under data
                    renderTableRows(tableId, [], columns, idField, basePath);
                }
                renderPagination(paginationId, pageInfo, function(page){ currentPage = page; load(); });
            }).catch(err => {
                console.error('列表加载失败', err);
            });
        }

        // bind search/reset
        if (searchFormId) {
            const form = document.getElementById(searchFormId);
            if (form) {
                const searchBtn = form.querySelector('#searchBtn');
                const resetBtn = form.querySelector('#resetBtn');
                if (searchBtn) searchBtn.addEventListener('click', function(){ currentPage = 1; load(); });
                if (resetBtn) resetBtn.addEventListener('click', function(){ form.reset(); currentPage = 1; load(); });
            }
        }

        load();
    };

})();

// Submit form helper: sends JSON body and redirects on success
(function(){
    function formToJson(form) {
        const fd = new FormData(form);
        const obj = {};
        for (const [k, v] of fd.entries()) {
            obj[k] = v;
        }
        return obj;
    }

    window.submitForm = function(formId, url, method, redirectUrl) {
        const form = document.getElementById(formId);
        if (!form) {
            console.error('form not found:', formId);
            return;
        }
        const payload = formToJson(form);
        fetch(url, {
            method: method || 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        })
        .then(r => r.json())
        .then(resp => {
            const code = resp && (resp.code || resp.status || resp.code === 0 ? resp.code : null);
            const success = (code === 0) || (code === 200) || (code === null);
            if (success) {
                // show toast then redirect/refresh shortly
                if (redirectUrl) {
                    if (window.showToast) window.showToast('success', '保存成功');
                    setTimeout(() => location.href = redirectUrl, 700);
                } else {
                    if (window.showToast) window.showToast('success', '保存成功');
                    setTimeout(() => location.reload(), 700);
                }
            } else {
                const msg = resp && (resp.message || JSON.stringify(resp));
                if (window.showToast) window.showToast('error', '保存失败: ' + msg);
                else alert('保存失败: ' + msg);
            }
        })
        .catch(err => {
            console.error('submit error', err);
            if (window.showToast) window.showToast('error', '保存时发生网络或服务器错误');
            else alert('保存时发生网络或服务器错误');
        });
    };
    
    // Submit a prepared JSON object: useful when form data needs custom preprocessing
    window.submitJson = function(payload, url, method, redirectUrl) {
        fetch(url, {
            method: method || 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
        .then(r => r.json())
        .then(resp => {
            const code = resp && (resp.code || resp.status || resp.code === 0 ? resp.code : null);
            const success = (code === 0) || (code === 200) || (code === null);
            if (success) {
                if (redirectUrl) {
                    if (window.showToast) window.showToast('success', '保存成功');
                    setTimeout(() => location.href = redirectUrl, 700);
                } else {
                    if (window.showToast) window.showToast('success', '保存成功');
                    setTimeout(() => location.reload(), 700);
                }
            } else {
                const msg = resp && (resp.message || JSON.stringify(resp));
                if (window.showToast) window.showToast('error', '保存失败: ' + msg);
                else alert('保存失败: ' + msg);
            }
        })
        .catch(err => {
            console.error('submitJson error', err);
            if (window.showToast) window.showToast('error', '提交失败，网络或服务器错误');
            else alert('提交失败，网络或服务器错误');
        });
    };
})();
