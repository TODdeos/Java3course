import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class XMLParserExample extends JFrame {
    private JTree xmlTree;

    public XMLParserExample() {
        setTitle("XML Parser with JTree");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        xmlTree = new JTree();
        JScrollPane scrollPane = new JScrollPane(xmlTree);

        add(scrollPane, BorderLayout.CENTER);

        // Парсинг и отображение XML
        try {
            parseAndDisplayXML();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка при парсинге XML", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void parseAndDisplayXML() throws IOException, SAXException {
        // Используем Digester для парсинга XML
        Digester digester = new Digester();
        digester.setValidating(false);

        // Устанавливаем правило для создания узлов дерева
        digester.addObjectCreate("*/node", DefaultMutableTreeNode.class);
        digester.addSetProperties("*/node");

        // Устанавливаем правило для добавления текстового содержимого в узел дерева
        digester.addCallMethod("*/node", "add", 1);
        digester.addCallParam("*/node", 0);

        // Чтение XML из файла
        try (InputStream inputStream = getClass().getResourceAsStream("/example.xml")) {
            DefaultMutableTreeNode rootNode = digester.parse(inputStream);

            // Создаем JTree с корневым узлом
            xmlTree.setModel(new DefaultTreeModel(rootNode));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            XMLParserExample example = new XMLParserExample();
            example.setVisible(true);
        });
    }
}

