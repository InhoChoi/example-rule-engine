package rulebook;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.NameValueReferableTypeConvertibleMap;
import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
import com.deliveredtechnologies.rulebook.model.RuleBook;

import java.util.function.Consumer;

public class RulebookMain {
    public static class HelloWorldRule {
        public RuleBook<Object> defineHelloWorldRules() {
            return RuleBookBuilder
                    .create()
                    .addRule(rule -> rule.withNoSpecifiedFactType().then(f -> System.out.print("Hello ")))
                    .addRule(rule -> rule.withNoSpecifiedFactType().then(f -> System.out.println("World")))
                    .build();
        }

    }

    static RuleBook ruleBook = RuleBookBuilder.create()
            .addRule(rule -> rule.withFactType(String.class)
                    .when(f -> f.containsKey("hello"))
                    .using("hello")
                    .then(System.out::print))
            .addRule(rule -> rule.withFactType(String.class)
                    .when(f -> f.containsKey("world")) // 조건
                    .using("world", "hello") // Facts를 사용할 데이터를 정한다
                    .then(new Consumer<NameValueReferableTypeConvertibleMap<String>>() {
                        @Override
                        public void accept(NameValueReferableTypeConvertibleMap<String> stringNameValueReferableTypeConvertibleMap) {
                            //Map를 통하여 접근할수가 이다
                            System.out.println(stringNameValueReferableTypeConvertibleMap.toString());
                        }
                    }))
            .build();

    public static void main(String[] args) {
        HelloWorldRule helloWorldRule = new HelloWorldRule();
        helloWorldRule
                .defineHelloWorldRules()
                .run(new FactMap<>());


        NameValueReferableMap factMap = new FactMap();
        factMap.setValue("hello", "Hello ");
        factMap.setValue("world", " World");
        ruleBook.run(factMap);
    }
}
