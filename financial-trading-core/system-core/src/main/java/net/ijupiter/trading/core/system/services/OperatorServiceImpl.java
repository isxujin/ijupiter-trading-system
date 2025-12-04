package net.ijupiter.trading.core.system.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.system.api.dto.OperatorDTO;
import net.ijupiter.trading.system.api.dto.OperatorQueryDTO;
import net.ijupiter.trading.system.api.services.OperatorService;
import net.ijupiter.trading.core.system.entities.Operator;
import net.ijupiter.trading.core.system.repositories.OperatorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * 操作员服务实现
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final OperatorRepository operatorRepository;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 实体转DTO
     */
    private OperatorDTO convertToDTO(Operator operator) {
        OperatorDTO dto = new OperatorDTO();
        
        // 手动复制属性，避免时间字段问题
        dto.setOperatorId(operator.getOperatorId());
        dto.setOperatorName(operator.getOperatorName());
        dto.setLoginName(operator.getLoginName());
        dto.setPassword(operator.getPassword());
        dto.setPhone(operator.getPhone());
        dto.setEmail(operator.getEmail());
        dto.setStatus(operator.getStatus());
        
        // 时间字段从实体复制到DTO
        dto.setCreateTime(operator.getCreateTime());
        dto.setUpdateTime(operator.getUpdateTime());
        
        // 格式化最后登录时间为字符串
        if (operator.getLastLoginTime() != null) {
            dto.setLastLoginTime(operator.getLastLoginTime().format(DATE_TIME_FORMATTER));
        }
        
        return dto;
    }

    /**
     * DTO转实体
     */
    private Operator convertToEntity(OperatorDTO dto) {
        Operator entity = new Operator();
        
        // 手动复制属性，避免时间字段问题
        entity.setOperatorId(dto.getOperatorId());
        entity.setOperatorName(dto.getOperatorName());
        entity.setLoginName(dto.getLoginName());
        entity.setPassword(dto.getPassword());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setStatus(dto.getStatus());
        
        // 时间字段从DTO复制，确保正确处理
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        
        // lastLoginTime字段需要特殊处理，因为DTO中是字符串
        if (dto.getLastLoginTime() != null) {
            entity.setLastLoginTime(LocalDateTime.parse(dto.getLastLoginTime(), DATE_TIME_FORMATTER));
        }
        
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperatorDTO> queryOperators(OperatorQueryDTO queryDTO) {
        // 实现查询逻辑
        return operatorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public OperatorDTO getOperatorById(String operatorId) {
        Optional<Operator> operator = operatorRepository.findById(operatorId);
        return operator.map(this::convertToDTO).orElse(null);
    }

    @Override
    public OperatorDTO getOperatorByLoginName(String loginName) {
        // 使用Optional包装可能不是Optional的返回值
        Operator operator = operatorRepository.findByLoginName(loginName);
        if (operator != null) {
            return convertToDTO(operator);
        }
        return null;
    }

    @Override
    @Transactional
    public OperatorDTO createOperator(OperatorDTO operatorDTO) {
        log.debug("创建操作员: {}", operatorDTO);
        
        Operator operator = convertToEntity(operatorDTO);
        operator.setCreateTime(LocalDateTime.now());
        Operator savedOperator = operatorRepository.save(operator);
        
        return convertToDTO(savedOperator);
    }

    @Override
    @Transactional
    public OperatorDTO updateOperator(OperatorDTO operatorDTO) {
        log.debug("更新操作员: {}", operatorDTO);
        
        Operator operator = operatorRepository.findById(operatorDTO.getOperatorId())
                .orElseThrow(() -> new RuntimeException("操作员不存在: " + operatorDTO.getOperatorId()));
        
        // 更新字段
        operator.setOperatorName(operatorDTO.getOperatorName());
        operator.setLoginName(operatorDTO.getLoginName());
        operator.setPassword(operatorDTO.getPassword());
        operator.setPhone(operatorDTO.getPhone());
        operator.setEmail(operatorDTO.getEmail());
        operator.setStatus(operatorDTO.getStatus());
        operator.setUpdateTime(LocalDateTime.now());
        
        Operator savedOperator = operatorRepository.save(operator);
        return convertToDTO(savedOperator);
    }

    @Override
    public Boolean deleteOperator(String operatorId) {
        log.debug("删除操作员: {}", operatorId);
        
        if (operatorRepository.existsById(operatorId)) {
            operatorRepository.deleteById(operatorId);
            return true;
        }
        return false;
    }

    @Override
    public Integer batchDeleteOperators(List<String> operatorIds) {
        log.debug("批量删除操作员: {}", operatorIds);
        
        int deletedCount = 0;
        for (String operatorId : operatorIds) {
            if (deleteOperator(operatorId)) {
                deletedCount++;
            }
        }
        return deletedCount;
    }

    @Override
    @Transactional
    public Boolean resetOperatorPassword(String operatorId, String newPassword) {
        log.debug("重置操作员密码: operatorId={}", operatorId);
        
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("操作员不存在: " + operatorId));
        
        operator.setPassword(newPassword);
        operator.setUpdateTime(LocalDateTime.now());
        operatorRepository.save(operator);
        
        return true;
    }

    @Override
    @Transactional
    public Boolean updateOperatorStatus(String operatorId, Integer status) {
        log.debug("更新操作员状态: operatorId={}, status={}", operatorId, status);
        
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("操作员不存在: " + operatorId));
        
        operator.setStatus(status);
        operator.setUpdateTime(LocalDateTime.now());
        operatorRepository.save(operator);
        
        return true;
    }

    @Override
    @Transactional
    public Boolean bindRoles(String operatorId, List<String> roleIds) {
        log.debug("为操作员绑定角色: operatorId={}, roles={}", operatorId, roleIds);
        
        // 实现角色绑定逻辑
        // 这里需要实现操作员-角色关联表的插入操作
        
        return true;
    }

    @Override
    @Transactional
    public Boolean unbindRoles(String operatorId, List<String> roleIds) {
        log.debug("解绑操作员角色: operatorId={}, roles={}", operatorId, roleIds);
        
        // 实现角色解绑逻辑
        // 这里需要实现操作员-角色关联表的删除操作
        
        return true;
    }

    // ==================== BaseService接口实现 ====================

    @Override
    @Transactional
    public OperatorDTO save(OperatorDTO entity) {
        log.debug("保存操作员实体: {}", entity);
        Operator operator = convertToEntity(entity);
        if (operator.getCreateTime() == null) {
            operator.setCreateTime(LocalDateTime.now());
        }
        Operator savedOperator = operatorRepository.save(operator);
        return convertToDTO(savedOperator);
    }

    @Override
    public Optional<OperatorDTO> findById(String id) {
        log.debug("根据ID查询操作员: {}", id);
        return operatorRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<OperatorDTO> findAll() {
        log.debug("查询所有操作员");
        return operatorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean existsById(String id) {
        log.debug("检查操作员是否存在: {}", id);
        return operatorRepository.existsById(id);
    }

    @Override
    public long count() {
        log.debug("统计操作员数量");
        return operatorRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        log.info("删除操作员: {}", id);
        operatorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(OperatorDTO entity) {
        log.info("删除操作员实体: {}", entity);
        if (entity.getOperatorId() != null) {
            operatorRepository.deleteById(entity.getOperatorId());
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        log.info("删除所有操作员");
        operatorRepository.deleteAll();
    }

    @Override
    @Transactional
    public OperatorDTO saveAndFlush(OperatorDTO entity) {
        log.debug("保存并刷新操作员实体: {}", entity);
        Operator operator = convertToEntity(entity);
        if (operator.getCreateTime() == null) {
            operator.setCreateTime(LocalDateTime.now());
        }
        Operator savedOperator = operatorRepository.saveAndFlush(operator);
        return convertToDTO(savedOperator);
    }

    @Override
    @Transactional
    public List<OperatorDTO> saveAll(List<OperatorDTO> entities) {
        log.debug("批量保存操作员实体: {}", entities.size());
        List<Operator> operators = entities.stream()
                .map(this::convertToEntity)
                .collect(java.util.stream.Collectors.toList());
        
        // 设置创建时间
        LocalDateTime now = LocalDateTime.now();
        operators.forEach(operator -> {
            if (operator.getCreateTime() == null) {
                operator.setCreateTime(now);
            }
        });
        
        List<Operator> savedOperators = operatorRepository.saveAll(operators);
        return savedOperators.stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<OperatorDTO> findAllById(List<String> ids) {
        log.debug("根据ID列表查询操作员: {}", ids);
        return operatorRepository.findAllById(ids).stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}