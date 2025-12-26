package net.ijupiter.trading.core.customer.configs;
//
//import lombok.extern.slf4j.Slf4j;
//import org.axonframework.eventsourcing.eventstore.DomainEventMessage;
//import org.axonframework.eventsourcing.eventstore.EventStoreException;
//import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
//import org.axonframework.modelling.command.AggregateIdentifier;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * 内存事件存储引擎（仅用于演示，生产环境应使用数据库存储）
// */
//@Slf4j
//public class InMemoryEventStorageEngine implements EventStorageEngine {
//
//    // 按聚合ID存储事件
//    private final Map<String, List<DomainEventMessage<?>>> eventStore = new ConcurrentHashMap<>();
//
//    // 全局事件序列（用于全局查询）
//    private final List<DomainEventMessage<?>> allEvents = new ArrayList<>();
//
//    @Override
//    public void appendEvents(List<? extends DomainEventMessage<?>> events) {
//        for (DomainEventMessage<?> event : events) {
//            String aggregateId = event.getAggregateIdentifier();
//
//            // 添加到聚合事件列表
//            eventStore.computeIfAbsent(aggregateId, k -> new ArrayList<>()).add(event);
//
//            // 添加到全局事件列表
//            allEvents.add(event);
//
//            log.debug("存储事件: {} - {}", aggregateId, event.getPayloadType().getSimpleName());
//        }
//    }
//
//    @Override
//    public Stream<? extends DomainEventMessage<?>> readEvents(String aggregateIdentifier, long firstSequenceNumber, long lastSequenceNumber) {
//        List<DomainEventMessage<?>> events = eventStore.getOrDefault(aggregateIdentifier, Collections.emptyList());
//
//        return events.stream()
//                .filter(event -> event.getSequenceNumber() >= firstSequenceNumber &&
//                                 event.getSequenceNumber() <= lastSequenceNumber);
//    }
//
//    @Override
//    public Optional<Long> lastSequenceNumberFor(String aggregateIdentifier) {
//        List<DomainEventMessage<?>> events = eventStore.getOrDefault(aggregateIdentifier, Collections.emptyList());
//
//        if (events.isEmpty()) {
//            return Optional.empty();
//        }
//
//        return Optional.of(events.get(events.size() - 1).getSequenceNumber());
//    }
//
//    @Override
//    public Stream<? extends DomainEventMessage<?>> readEvents(long offset, int maxSize) {
//        return allEvents.stream()
//                .skip(offset)
//                .limit(maxSize);
//    }
//
//    @Override
//    public void storeSnapshot(DomainEventMessage<?> snapshot) {
//        // 简化实现，直接将快照当作事件存储
//        String aggregateId = snapshot.getAggregateIdentifier();
//        eventStore.computeIfAbsent(aggregateId, k -> new ArrayList<>()).add(snapshot);
//        allEvents.add(snapshot);
//
//        log.debug("存储快照: {}", aggregateId);
//    }
//
//    @Override
//    public Optional<DomainEventMessage<?>> readSnapshot(String aggregateIdentifier) {
//        List<DomainEventMessage<?>> events = eventStore.getOrDefault(aggregateIdentifier, Collections.emptyList());
//
//        if (events.isEmpty()) {
//            return Optional.empty();
//        }
//
//        // 返回最后一个事件（简化实现，实际应该单独存储快照）
//        return Optional.of(events.get(events.size() - 1));
//    }
//
//    @Override
//    public void pruneSnapshots(String type, DomainEventMessage<?> snapshot) {
//        // 内存实现不需要清理
//        log.debug("清理快照: {}", type);
//    }
//
//    @Override
//    public long lastSnapshotTimestamp(String aggregateIdentifier) {
//        List<DomainEventMessage<?>> events = eventStore.getOrDefault(aggregateIdentifier, Collections.emptyList());
//
//        if (events.isEmpty()) {
//            return Long.MIN_VALUE;
//        }
//
//        return events.stream()
//                .filter(event -> !"DomainEventMessage".equals(event.getClass().getSimpleName()))
//                .findFirst()
//                .map(event -> event.getTimestamp().toEpochMilli())
//                .orElse(Long.MIN_VALUE);
//    }
//
//    @Override
//    public void purgeEvents(String aggregateIdentifier) {
//        eventStore.remove(aggregateIdentifier);
//        log.debug("清除事件: {}", aggregateIdentifier);
//    }
//}