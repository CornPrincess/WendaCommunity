package com.minmin.wenda.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author corn
 * @version 1.0.0
 */

@Service
public class SensitiveService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    // TODO InitializingBean
    // bean初始化时读入敏感词
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);

            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                addWord(lineTxt.trim());
            }
            read.close();

        } catch (Exception e) {
            logger.error("读取敏感词文件失败" + e.getMessage());
        }
    }

    // 将关键词加入字典树
    private void addWord(String lineTxt) {
        TrieNode tempNode = rootNode;

        for (int i = 0; i < lineTxt.length(); i++) {
            Character c = lineTxt.charAt(i);

            // 去除干扰词
            if(isSymbol(c)) {
                continue;
            }

            TrieNode node = tempNode.getSubNode(c);

            // 如果不存在c就添加上去
            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }

            // 如果存在就到子节点上去

            tempNode = node;

            if (i == lineTxt.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    // 前缀树
    private class TrieNode {
        // 是不是关键词的结尾
        private boolean end = false;

        // 当前节点下所有子节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeywordEnd() {
            return end;
        }

        void setKeywordEnd(boolean end) {
            this.end = end;
        }
    }

    private TrieNode rootNode = new TrieNode();

    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        String replacement = "***";

        // 设置三个指针
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

        while (position < text.length()) {
            char c = text.charAt(position);

            // 如果是非法词，如空格等，直接跳过下一个
            if(isSymbol(c)) {
                if(tempNode == rootNode) {
                    result.append(c);
                    begin++;
                }

                position++;
                continue;
            }

            tempNode = tempNode.getSubNode(c);


            //  该节点字符不在字典树上
            if (tempNode == null) {
                result.append(text.charAt(begin));

                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            } else if(tempNode.isKeywordEnd()) {
                // 发现敏感词
                result.append(replacement);

                position = position + 1;
                begin = position;
                tempNode = rootNode;
            } else  {
                position++;
            }

        }
        result.append(text.substring(begin));
        return result.toString();
    }

    // 优化过滤，如过滤掉：色 情
    private boolean isSymbol(char c) {
        int ic = (int) c;

        // 0x2E80- 0x9FFF 代表东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }


    public static void main(String[] args) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("赌博");

        System.out.print(s.filter("  v 你好色v 情haha"));
    }


}
