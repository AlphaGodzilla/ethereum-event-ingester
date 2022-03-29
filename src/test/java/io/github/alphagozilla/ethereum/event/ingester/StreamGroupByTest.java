package io.github.alphagozilla.ethereum.event.ingester;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 10:45
 */
@Slf4j
public class StreamGroupByTest {
    public static class Item {
        private String name;
        private String value;

        public Item(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public static Item of(String name, String value) {
            return new Item(name, value);
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    @Test
    public void test() {
        List<Item> items = List.of(
                Item.of("apple", "haha"),
                Item.of("apple", "hehe"),
                Item.of("orange", "keke")
        );
        Map<String, List<Item>> collect = items.stream().collect(Collectors.groupingBy(Item::getName, Collectors.toList()));
        log.info("{}", collect);
    }
}
