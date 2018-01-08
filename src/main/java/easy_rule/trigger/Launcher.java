/**
 * The MIT License
 * <p>
 * Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package easy_rule.trigger;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Yml를 이용한 Rule 및 MVEL를 이용한Rule
 */
public class Launcher {

    public void run(String[] args) throws FileNotFoundException {
        Facts facts = new Facts();
        facts.put("event", new Event());
        facts.put("sender", new Sender());

        MVELRule triggerRule = MVELRuleFactory.createRuleFrom(new File("/Users/inhochoi/IdeaProjects/rule-engine/src/main/resources/trigger-purchase.yml"));

        Rules rules = new Rules();
        rules.register(triggerRule);

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }

    public static class Event {
        public String getName() {
            return "purchase";
        }
    }

    public static class Sender {
        public void send(String eventName) {
            System.out.println("Send Triggering! " + eventName);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Launcher().run(args);
    }
}
