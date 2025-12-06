-- ===================================================================
-- 客户管理模块数据库表结构定义
-- ===================================================================

-- 创建客户表
CREATE TABLE IF NOT EXISTS customer (
    customer_id VARCHAR(36) PRIMARY KEY COMMENT '客户ID',
    customer_code VARCHAR(50) NOT NULL UNIQUE COMMENT '客户编号',
    customer_name VARCHAR(100) NOT NULL COMMENT '客户名称',
    id_card_number VARCHAR(20) NOT NULL UNIQUE COMMENT '身份证号',
    phone_number VARCHAR(20) NOT NULL COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    address VARCHAR(200) COMMENT '联系地址',
    risk_level VARCHAR(20) NOT NULL COMMENT '风险等级',
    customer_status VARCHAR(20) NOT NULL COMMENT '客户状态',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否激活',
    last_login_time TIMESTAMP NULL COMMENT '最后登录时间',
    cancel_time TIMESTAMP NULL COMMENT '注销时间',
    cancel_reason VARCHAR(200) COMMENT '注销原因',
    freeze_time TIMESTAMP NULL COMMENT '冻结时间',
    freeze_reason VARCHAR(200) COMMENT '冻结原因',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    version BIGINT NOT NULL DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户表';

-- 创建客户表索引
CREATE INDEX idx_customer_code ON customer(customer_code);
CREATE INDEX idx_customer_status ON customer(customer_status);
CREATE INDEX idx_phone_number ON customer(phone_number);
CREATE INDEX idx_id_card_number ON customer(id_card_number);
CREATE INDEX idx_customer_status_risk ON customer(customer_status, risk_level);

-- 创建交易账户基本信息表
CREATE TABLE IF NOT EXISTS trading_account_basic_info (
    basic_info_id VARCHAR(36) PRIMARY KEY COMMENT '交易账户基本信息ID',
    account_id VARCHAR(36) NOT NULL COMMENT '交易账户ID',
    account_code VARCHAR(50) NOT NULL UNIQUE COMMENT '账户编号',
    customer_id VARCHAR(36) NOT NULL COMMENT '客户ID',
    account_name VARCHAR(100) NOT NULL COMMENT '账户名称',
    account_status VARCHAR(20) NOT NULL COMMENT '账户状态',
    account_type VARCHAR(20) NOT NULL COMMENT '交易账户类型',
    exchange_code VARCHAR(20) NOT NULL COMMENT '交易所代码',
    exchange_name VARCHAR(100) NOT NULL COMMENT '交易所名称',
    exchange_account_number VARCHAR(50) NOT NULL COMMENT '交易所账号',
    trading_password VARCHAR(500) COMMENT '交易密码（加密存储）',
    fund_password VARCHAR(500) COMMENT '资金密码（加密存储）',
    api_key VARCHAR(500) COMMENT 'API密钥',
    api_secret VARCHAR(500) COMMENT 'API密钥密钥',
    trading_product VARCHAR(100) NOT NULL COMMENT '交易产品品种',
    close_time TIMESTAMP NULL COMMENT '关闭时间',
    close_reason VARCHAR(200) COMMENT '关闭原因',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    version BIGINT NOT NULL DEFAULT 0 COMMENT '版本号',
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易账户基本信息表';

-- 创建交易账户基本信息表索引
CREATE INDEX idx_account_code ON trading_account_basic_info(account_code);
CREATE INDEX idx_customer_id ON trading_account_basic_info(customer_id);
CREATE INDEX idx_exchange_code ON trading_account_basic_info(exchange_code);
CREATE INDEX idx_account_type ON trading_account_basic_info(account_type);
CREATE INDEX idx_account_status ON trading_account_basic_info(account_status);
CREATE INDEX idx_customer_status ON trading_account_basic_info(customer_id, account_status);

-- 创建交易账户持仓表
CREATE TABLE IF NOT EXISTS trading_account_position (
    position_id VARCHAR(36) PRIMARY KEY COMMENT '交易账户持仓ID',
    account_id VARCHAR(36) NOT NULL COMMENT '交易账户ID',
    account_code VARCHAR(50) NOT NULL COMMENT '账户编号',
    customer_id VARCHAR(36) NOT NULL COMMENT '客户ID',
    position_shares DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '持仓份额',
    available_shares DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '可用份额',
    frozen_shares DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '冻结份额',
    daily_buy_shares DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '当日买入份额',
    daily_sell_shares DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '当日卖出份额',
    daily_net_shares DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '当日净发生份额',
    total_market_value DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '总持仓市值',
    available_market_value DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '可用持仓市值',
    frozen_market_value DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '冻结持仓市值',
    last_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    version BIGINT NOT NULL DEFAULT 0 COMMENT '版本号',
    FOREIGN KEY (account_id) REFERENCES trading_account_basic_info(account_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易账户持仓表';

-- 创建交易账户持仓表索引
CREATE INDEX idx_account_code ON trading_account_position(account_code);
CREATE INDEX idx_customer_id ON trading_account_position(customer_id);
CREATE INDEX idx_account_id ON trading_account_position(account_id);
CREATE INDEX idx_last_update_time ON trading_account_position(last_update_time);

-- 创建资金账户基本信息表
CREATE TABLE IF NOT EXISTS fund_account_basic_info (
    basic_info_id VARCHAR(36) PRIMARY KEY COMMENT '资金账户基本信息ID',
    account_id VARCHAR(36) NOT NULL COMMENT '资金账户ID',
    account_code VARCHAR(50) NOT NULL UNIQUE COMMENT '账户编号',
    customer_id VARCHAR(36) NOT NULL COMMENT '客户ID',
    account_name VARCHAR(100) NOT NULL COMMENT '账户名称',
    account_status VARCHAR(20) NOT NULL COMMENT '账户状态',
    account_type VARCHAR(20) NOT NULL COMMENT '资金账户类型',
    bank_card_number VARCHAR(20) NOT NULL COMMENT '银行卡号',
    bank_code VARCHAR(10) NOT NULL COMMENT '银行代码',
    bank_name VARCHAR(100) NOT NULL COMMENT '银行名称',
    holder_name VARCHAR(100) NOT NULL COMMENT '持卡人姓名',
    currency VARCHAR(10) NOT NULL COMMENT '币种',
    close_time TIMESTAMP NULL COMMENT '关闭时间',
    close_reason VARCHAR(200) COMMENT '关闭原因',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    version BIGINT NOT NULL DEFAULT 0 COMMENT '版本号',
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资金账户基本信息表';

-- 创建资金账户基本信息表索引
CREATE INDEX idx_account_code ON fund_account_basic_info(account_code);
CREATE INDEX idx_customer_id ON fund_account_basic_info(customer_id);
CREATE INDEX idx_account_type ON fund_account_basic_info(account_type);
CREATE INDEX idx_account_status ON fund_account_basic_info(account_status);
CREATE INDEX idx_customer_status ON fund_account_basic_info(customer_id, account_status);
CREATE INDEX idx_bank_card_number ON fund_account_basic_info(bank_card_number);

-- 创建资金账户余额表
CREATE TABLE IF NOT EXISTS fund_account_balance (
    balance_id VARCHAR(36) PRIMARY KEY COMMENT '资金账户余额ID',
    account_id VARCHAR(36) NOT NULL COMMENT '资金账户ID',
    account_code VARCHAR(50) NOT NULL COMMENT '账户编号',
    customer_id VARCHAR(36) NOT NULL COMMENT '客户ID',
    balance DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '账户余额',
    available_balance DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '可用余额',
    frozen_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '冻结金额',
    total_balance DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '总余额',
    daily_deposit_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '当日入金金额',
    daily_withdraw_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '当日出金金额',
    daily_net_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000 COMMENT '当日净发生金额',
    last_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    version BIGINT NOT NULL DEFAULT 0 COMMENT '版本号',
    FOREIGN KEY (account_id) REFERENCES fund_account_basic_info(account_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资金账户余额表';

-- 创建资金账户余额表索引
CREATE INDEX idx_account_code ON fund_account_balance(account_code);
CREATE INDEX idx_customer_id ON fund_account_balance(customer_id);
CREATE INDEX idx_account_id ON fund_account_balance(account_id);
CREATE INDEX idx_last_update_time ON fund_account_balance(last_update_time);

-- 创建客户事件表（Axon事件存储）
CREATE TABLE IF NOT EXISTS customer_event (
    global_index BIGINT AUTO_INCREMENT PRIMARY KEY,
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    event_identifier VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    event_data BLOB NOT NULL,
    meta_data BLOB,
    timestamp VARCHAR(255) NOT NULL,
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255) NULL,
    UNIQUE KEY ix_unique_event (aggregate_identifier, sequence_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户事件表';

-- 创建交易账户事件表（Axon事件存储）
CREATE TABLE IF NOT EXISTS trading_account_event (
    global_index BIGINT AUTO_INCREMENT PRIMARY KEY,
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    event_identifier VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    event_data BLOB NOT NULL,
    meta_data BLOB,
    timestamp VARCHAR(255) NOT NULL,
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255) NULL,
    UNIQUE KEY ix_unique_event (aggregate_identifier, sequence_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易账户事件表';

-- 创建资金账户事件表（Axon事件存储）
CREATE TABLE IF NOT EXISTS fund_account_event (
    global_index BIGINT AUTO_INCREMENT PRIMARY KEY,
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    event_identifier VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    event_data BLOB NOT NULL,
    meta_data BLOB,
    timestamp VARCHAR(255) NOT NULL,
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255) NULL,
    UNIQUE KEY ix_unique_event (aggregate_identifier, sequence_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资金账户事件表';

-- 创建快照表
CREATE TABLE IF NOT EXISTS customer_snapshot (
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    snapshot_data BLOB NOT NULL,
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255) NULL,
    timestamp VARCHAR(255) NOT NULL,
    PRIMARY KEY (aggregate_identifier, sequence_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户快照表';

CREATE TABLE IF NOT EXISTS trading_account_snapshot (
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    snapshot_data BLOB NOT NULL,
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255) NULL,
    timestamp VARCHAR(255) NOT NULL,
    PRIMARY KEY (aggregate_identifier, sequence_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易账户快照表';

CREATE TABLE IF NOT EXISTS fund_account_snapshot (
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    snapshot_data BLOB NOT NULL,
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255) NULL,
    timestamp VARCHAR(255) NOT NULL,
    PRIMARY KEY (aggregate_identifier, sequence_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资金账户快照表';

-- 初始化示例数据（可选）
-- 示例客户数据
INSERT INTO customer (customer_id, customer_code, customer_name, id_card_number, phone_number, email, address, risk_level, customer_status, is_active) 
VALUES ('C10001', 'C10001', '张三', '110101199001011234', '13800138000', 'zhangsan@example.com', '北京市朝阳区', 'R2', 'NORMAL', TRUE);

-- 示例交易账户基本信息数据
INSERT INTO trading_account_basic_info (basic_info_id, account_id, account_code, customer_id, account_name, account_status, account_type, exchange_code, exchange_name, exchange_account_number, trading_product) 
VALUES ('TBI10001', 'TA10001', 'TA10001', 'C10001', '张三证券账户', 'NORMAL', 'STOCK', 'SHSE', '上海证券交易所', '1234567890', 'A股');

-- 示例交易账户持仓数据
INSERT INTO trading_account_position (position_id, account_id, account_code, customer_id, position_shares, available_shares, frozen_shares, daily_buy_shares, daily_sell_shares, daily_net_shares, total_market_value, available_market_value, frozen_market_value) 
VALUES ('TP10001', 'TA10001', 'TA10001', 'C10001', 1000.0000, 800.0000, 200.0000, 100.0000, 50.0000, 50.0000, 10000.0000, 8000.0000, 2000.0000);

-- 示例资金账户基本信息数据
INSERT INTO fund_account_basic_info (basic_info_id, account_id, account_code, customer_id, account_name, account_status, account_type, bank_card_number, bank_code, bank_name, holder_name, currency) 
VALUES ('FBI10001', 'FA10001', 'FA10001', 'C10001', '张三资金账户', 'NORMAL', 'MAIN', '6225881234567890', 'ICBC', '中国工商银行', '张三', 'CNY');

-- 示例资金账户余额数据
INSERT INTO fund_account_balance (balance_id, account_id, account_code, customer_id, balance, available_balance, frozen_amount, total_balance, daily_deposit_amount, daily_withdraw_amount, daily_net_amount) 
VALUES ('FB10001', 'FA10001', 'FA10001', 'C10001', 10000.0000, 8000.0000, 2000.0000, 10000.0000, 1000.0000, 500.0000, 500.0000);