/*
MIT License
Copyright (c) 2020 Max Dobbs

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;

class Lakshayati extends Thread {

    // The memory limit restricts recursion in Lakshayati programs.
    // Feel free to change it according to your computer's storage space.
    // "memory_limit” restringe la recursividad en los programas de Lakshayati.
    // Puede cambiarlo de acuerdo con el almacenamiento de su computadora.
    // “memory_limit”限制Lakshayati程序中的递归。
    // 您可以根据计算机的存储空间进行更改。
    public static final int memory_limit = 32767;

    public static final String directory = "Files 文件 Archivos" + File.separator;
    public static boolean bool_continue_program;
    public static boolean bool_no_program_running = true;
    public static boolean bool_change_format = true;
    public static Hashtable<String, String> variables = new Hashtable<String, String>();
    public static LinkedList<LinkedList<String>> list_of_terms = new LinkedList<LinkedList<String>>();
    public static LinkedList<LinkedList<Boolean>> list_of_types = new LinkedList<LinkedList<Boolean>>();
    public static JTextArea output = new JTextArea(12, 48);
    public static JButton play_button = new JButton();
    public static String most_recent_file = "";

    public void run() {
        int line_iterator = 0;
        while (line_iterator < 251 && bool_continue_program && list_of_types.size() != 0 &&
                list_of_types.size() <= memory_limit) {
            LinkedList<String> line_of_terms = list_of_terms.get(0);
            LinkedList<Boolean> parsed_line_of_code = list_of_types.get(0);
            list_of_terms.remove(0);
            list_of_types.remove(0);
            if (parsed_line_of_code.size() != 1) {
                if (parsed_line_of_code.get(0)) {
                    String string_value = line_of_terms.get(0);
                    for (int i = 1; i < parsed_line_of_code.size(); i += 1) {
                        if (parsed_line_of_code.get(i))
                            string_value += line_of_terms.get(i);
                        else if (line_of_terms.get(i).equals("/") || line_of_terms.get(i).equals("／"))
                            string_value += variables.get("/");
                        else if (variables.containsKey(line_of_terms.get(i)))
                            string_value += variables.get(line_of_terms.get(i));
                    }
                    organize_code(string_value + " ");
                } else {
                    String string_value = "";
                    for (int i = 1; i < parsed_line_of_code.size(); i += 1) {
                        if (parsed_line_of_code.get(i))
                            string_value += line_of_terms.get(i);
                        else if (line_of_terms.get(i).equals("/") || line_of_terms.get(i).equals("／"))
                            string_value += variables.get("/");
                        else if (variables.containsKey(line_of_terms.get(i)))
                            string_value += variables.get(line_of_terms.get(i));
                    }
                    if (!line_of_terms.get(0).equals("/") && !line_of_terms.get(0).equals("／")) {
                        if (string_value.length() != 0)
                            variables.put(line_of_terms.get(0), string_value);
                        else
                            variables.remove(line_of_terms.get(0));
                    } else {
                        variables.put("/", string_value);
                        output.setText(string_value);
                        break;
                    }
                }
            } else {
                if (parsed_line_of_code.get(0))
                    organize_code(line_of_terms.get(0) + " ");
                else if (variables.containsKey(line_of_terms.get(0)))
                    organize_code(variables.get(line_of_terms.get(0)) + " ");
            }
            line_iterator++;
        }
        if (bool_continue_program && list_of_types.size() != 0 && list_of_types.size() <= memory_limit) {
            Lakshayati thread = new Lakshayati();
            thread.start();
        } else {
            bool_continue_program = false;
            if (list_of_types.size() > memory_limit)
                output.setText("Error: The recursion limit has been exceeded\n\n错误：已超过递归限制\n\n" +
                        "Error: Se superó el límite de recursividad");
            list_of_terms.clear();
            list_of_types.clear();
            variables.clear();
            play_button.setText("▶");
            bool_change_format = true;
            bool_no_program_running = true;
        }
    }

    public static void organize_code(String code_string) {
        list_of_terms.add(0, new LinkedList<String>());
        list_of_types.add(0, new LinkedList<Boolean>());
        String term = "";
        boolean bool_outside_string = true;
        boolean bool_analyzing_string = false;
        byte string_category = 0;
        int position_in_list = 0;
        int number_of_quotes = 0;
        int end_quotes_needed = 0;
        char quotation_mark = '"';
        String quote_holder = "";
        int i = 0;
        while (bool_continue_program && i < code_string.length()) {
            char current_character = code_string.charAt(i);
            if (bool_outside_string) {
                if (current_character == ' ' || current_character == '　' || current_character == '\t')
                    i++;
                else if (current_character == '\n') {
                    if (list_of_types.get(position_in_list).size() != 0) {
                        position_in_list++;
                        list_of_terms.add(position_in_list, new LinkedList<String>());
                        list_of_types.add(position_in_list, new LinkedList<Boolean>());
                    }
                    i++;
                } else if (current_character == '"' || current_character == '“' || current_character == '”' ||
                        current_character == '„') {
                    bool_outside_string = false;
                    string_category = 1;
                    number_of_quotes = 1;
                    i++;
                } else if (current_character == '\'' || current_character == '‘' || current_character == '’' ||
                        current_character == '‚') {
                    bool_outside_string = false;
                    string_category = 2;
                    number_of_quotes = 1;
                    i++;
                } else if (current_character == '«' || current_character == '»') {
                    bool_outside_string = false;
                    string_category = 3;
                    number_of_quotes = 1;
                    i++;
                } else if (current_character == '‹' || current_character == '›') {
                    bool_outside_string = false;
                    string_category = 4;
                    number_of_quotes = 1;
                    i++;
                } else if (current_character == '「' || current_character == '『' || current_character == '《' ||
                        current_character == '〈') {
                    bool_outside_string = false;
                    string_category = 0;
                    number_of_quotes = 1;
                    quotation_mark = current_character;
                    i++;
                } else if (current_character != '」' && current_character != '』' && current_character != '》' &&
                        current_character != '〉') {
                    term = "";
                    while (code_string.charAt(i) != ' ' && code_string.charAt(i) != '　' &&
                            code_string.charAt(i) != '\t' && code_string.charAt(i) != '\n') {
                        if (code_string.charAt(i) == '"' || code_string.charAt(i) == '\'' ||
                                code_string.charAt(i) == '«' || code_string.charAt(i) == '»' ||
                                code_string.charAt(i) == '‹' || code_string.charAt(i) == '›' ||
                                code_string.charAt(i) == '「' || code_string.charAt(i) == '『' ||
                                code_string.charAt(i) == '《' || code_string.charAt(i) == '〈' ||
                                code_string.charAt(i) == '」' || code_string.charAt(i) == '』' ||
                                code_string.charAt(i) == '》' || code_string.charAt(i) == '〉' ||
                                code_string.charAt(i) == '„' || code_string.charAt(i) == '‚' ||
                                code_string.charAt(i) == '“' || code_string.charAt(i) == '”' ||
                                code_string.charAt(i) == '‘' || code_string.charAt(i) == '’') {
                            output.setText("Error: Spaces are required to separate strings and variables\n\n" +
                                    "错误：需要空格来分隔字符串和变量\n\n" +
                                    "Error: Se requieren espacios para separar variables y cadenas de caracteres");
                            bool_continue_program = false;
                            break;
                        }
                        term += code_string.charAt(i);
                        i++;
                    }
                    list_of_terms.get(position_in_list).add(term);
                    list_of_types.get(position_in_list).add(false);
                } else {
                    output.setText("Error: " + current_character + " cannot be used to start a string\n\n错误：" +
                            current_character + "不能用来开始字符串\n\nError: " + current_character +
                            " no puede iniciar una cadena de caracteres");
                    bool_continue_program = false;
                }
            } else if (string_category != 0) {
                if (bool_analyzing_string) {
                    if ((string_category == 1 && (current_character == '"' || current_character == '“' ||
                            current_character == '”' || current_character == '„')) || (string_category == 2 &&
                            (current_character == '\'' || current_character == '‘' || current_character == '’' ||
                                    current_character == '‚')) || (string_category == 3 && (current_character == '«' ||
                            current_character == '»')) || (string_category == 4 && (current_character == '‹' ||
                            current_character == '›'))) {
                        int j = 1;
                        int index_deleted = term.length() - 1;
                        while (code_string.charAt(i - j) == '/' || code_string.charAt(i - j) == '／') {
                            if (j % 2 == 1) {
                                term = term.substring(0, index_deleted) +
                                        term.substring(index_deleted + 1, term.length());
                                index_deleted--;
                            }
                            j++;
                        }
                        if (j % 2 == 1) {
                            end_quotes_needed--;
                            quote_holder += current_character;
                        } else {
                            term += current_character;
                            quote_holder = "";
                        }
                    } else {
                        end_quotes_needed = number_of_quotes;
                        term += quote_holder + current_character;
                        quote_holder = "";
                    }
                    if (end_quotes_needed == 0) {
                        current_character = code_string.charAt(i + 1);
                        if (current_character == ' ' || current_character == '　' || current_character == '\t' ||
                                current_character == '\n') {
                            list_of_terms.get(position_in_list).add(term);
                            list_of_types.get(position_in_list).add(true);
                            bool_outside_string = true;
                            bool_analyzing_string = false;
                        } else {
                            output.setText("Error: Spaces are required to separate strings and variables\n\n" +
                                    "错误：需要空格来分隔字符串和变量\n\n" + "" +
                                    "Error: Se requieren espacios para separar variables y cadenas de caracteres");
                            bool_continue_program = false;
                        }
                    }
                    i++;
                } else {
                    if ((string_category == 1 && (current_character == '"' || current_character == '“' ||
                            current_character == '”' || current_character == '„')) || (string_category == 2 &&
                            (current_character == '\'' || current_character == '‘' || current_character == '’' ||
                                    current_character == '‚')) || (string_category == 3 && (current_character == '«' ||
                            current_character == '»')) || (string_category == 4 && (current_character == '‹' ||
                            current_character == '›'))) {
                        number_of_quotes++;
                        i++;
                    } else if (number_of_quotes % 2 == 1) {
                        bool_analyzing_string = true;
                        end_quotes_needed = number_of_quotes;
                        term = Character.toString(current_character);
                        quote_holder = "";
                        i++;
                    } else {
                        if (current_character == ' ' || current_character == '　' || current_character == '\t' ||
                                current_character == '\n') {
                            list_of_terms.get(position_in_list).add("");
                            list_of_types.get(position_in_list).add(true);
                            bool_outside_string = true;
                            bool_analyzing_string = false;
                        } else {
                            output.setText("Error: Spaces are required to separate strings and variables\n\n" +
                                    "错误：需要空格来分隔字符串和变量\n\n" +
                                    "Error: Espacios deben separar variables y cadenas de caracteres");
                            bool_continue_program = false;
                        }
                    }
                }
            } else {
                if (bool_analyzing_string) {
                    if (current_character == quotation_mark) {
                        int j = 1;
                        int index_deleted = term.length() - 1;
                        while (code_string.charAt(i - j) == '/' || code_string.charAt(i - j) == '／') {
                            if (j % 2 == 1) {
                                term = term.substring(0, index_deleted) +
                                        term.substring(index_deleted + 1, term.length());
                                index_deleted--;
                            }
                            j++;
                        }
                        if (j % 2 == 1) {
                            end_quotes_needed--;
                            quote_holder += current_character;
                        } else {
                            term += current_character;
                            quote_holder = "";
                        }
                    } else {
                        end_quotes_needed = number_of_quotes;
                        term += quote_holder + current_character;
                        quote_holder = "";
                    }
                    if (end_quotes_needed == 0) {
                        current_character = code_string.charAt(i + 1);
                        if (current_character == ' ' || current_character == '　' || current_character == '\t' ||
                                current_character == '\n') {
                            list_of_terms.get(position_in_list).add(term);
                            list_of_types.get(position_in_list).add(true);
                            bool_outside_string = true;
                            bool_analyzing_string = false;
                        } else {
                            output.setText("Error: Spaces are required to separate strings and variables\n\n" +
                                    "错误：需要空格来分隔字符串和变量\n\n" +
                                    "Error: Espacios deben separar variables y cadenas de caracteres");
                            bool_continue_program = false;
                        }
                    }
                    i++;
                } else {
                    if (current_character == quotation_mark) {
                        number_of_quotes++;
                        i++;
                    } else {
                        if (current_character == '/' || current_character == '／') {
                            int j = i;
                            while (code_string.charAt(j) == '/' || code_string.charAt(j) == '／')
                                j++;
                            if (code_string.charAt(j) == quotation_mark) {
                                int k = 1;
                                int index_deleted = j - 1;
                                i++;
                                while (code_string.charAt(j - k) == '/' || code_string.charAt(j - k) == '／') {
                                    if (k % 2 == 1) {
                                        code_string = code_string.substring(0, index_deleted) +
                                                code_string.substring(index_deleted + 1, code_string.length());
                                        index_deleted--;
                                        i--;
                                    }
                                    k++;
                                }
                            }
                        }
                        bool_analyzing_string = true;
                        end_quotes_needed = number_of_quotes;
                        term = "";
                        quote_holder = "";
                        if (quotation_mark == '「')
                            quotation_mark = '」';
                        else if (quotation_mark == '『')
                            quotation_mark = '』';
                        else if (quotation_mark == '《')
                            quotation_mark = '》';
                        else
                            quotation_mark = '〉';
                    }
                }
            }
        }
        if (bool_continue_program && !bool_outside_string) {
            output.setText("Error: A string has not been terminated\n\n错误：字符串尚未终止\n\n" +
                    "Error: No se ha minado una cadena de caracteres");
            bool_continue_program = false;
        } else if (list_of_types.get(position_in_list).size() == 0) {
            list_of_terms.remove(position_in_list);
            list_of_types.remove(position_in_list);
        }
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Lakshayati");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea code = new JTextArea(12, 48);
        JButton format_button = new JButton();
        JButton open_file_button = new JButton();
        JButton save_button = new JButton();
        JButton delete_button = new JButton();
        JTextField input_file_name = new JTextField(30);
        JLabel side_message = new JLabel("File Name 文件名 Nombre del archivo:");
        JScrollPane output_scroll = new JScrollPane(output);
        output_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane code_scroll = new JScrollPane(code);
        code_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        code_scroll.setPreferredSize(new Dimension(0, 180));
        output_scroll.setPreferredSize(new Dimension(0, 180));
        output.setEditable(false);
        play_button.setText("▶");
        format_button.setText("⁋");
        open_file_button.setText("Open　打开　Abrir");
        save_button.setText("Save　保存　Guardar");
        delete_button.setText("Delete　删除　Eliminar");
        code.setText("/ \"Hello, world!\"");
        output.setText("Hello, world!");
        Font font_15 = new Font("Arial Unicode MS", Font.PLAIN, 15);
        Font font_18 = new Font("Monospaced", Font.PLAIN, 18);
        play_button.setFont(font_15);
        format_button.setFont(new Font("Arial Unicode MS", Font.PLAIN, 21));
        open_file_button.setFont(font_15);
        save_button.setFont(font_15);
        code.setFont(font_18);
        output.setFont(font_18);
        input_file_name.setFont(font_15);
        side_message.setFont(font_15);
        window.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 20, 20, 20);
        c.ipady = 4;
        c.gridx = 0;
        c.gridy = 1;
        window.add(play_button, c);
        c.gridx = 1;
        window.add(format_button, c);
        c.gridx = 2;
        window.add(open_file_button, c);
        c.gridx = 3;
        window.add(save_button, c);
        c.gridx = 4;
        window.add(delete_button, c);
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        c.gridheight = 3;
        window.add(code_scroll, c);
        c.gridy = 5;
        window.add(output_scroll, c);
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        window.add(side_message, c);
        c.gridx = 2;
        c.gridwidth = 3;
        window.add(input_file_name, c);
        window.pack();
        Dimension size = window.getSize();
        window.setSize(size.width, size.height + 36);
        window.setLocationRelativeTo(null);
        try {
            Files.createDirectories(Paths.get("Files 文件 Archivos"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(window, e, "", JOptionPane.ERROR_MESSAGE);
        }

        play_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bool_no_program_running && !bool_continue_program) {
                    bool_change_format = false;
                    if (code.getText().replace(" ", "").replace("　", "").replace("\n", "").replace("\t", "").length()
                            != 0) {
                        output.setText("");
                        bool_continue_program = true;
                        variables.put("/", "");
                        organize_code(code.getText() + " ");
                        if (bool_continue_program && list_of_types.size() != 0 && list_of_types.size()
                                <= memory_limit) {
                            play_button.setText("■");
                            bool_no_program_running = false;
                            Lakshayati thread = new Lakshayati();
                            thread.start();
                        } else {
                            bool_continue_program = false;
                            if (list_of_types.size() > memory_limit)
                                JOptionPane.showMessageDialog(window, "Code is too long\n\n代码太长\n\n" +
                                        "El código es demasiado largo\n", "", JOptionPane.ERROR_MESSAGE);
                            list_of_terms.clear();
                            list_of_types.clear();
                            variables.clear();
                            bool_change_format = true;
                        }
                    } else {
                        output.setText("");
                        bool_change_format = true;
                    }
                } else {
                    bool_continue_program = false;
                }
            }
        });

        format_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bool_change_format) {
                    if (format_button.getText().equals("⁋")) {
                        format_button.setText("¶");
                        output.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                        code.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                        output.setText(output.getText());
                        code.setText(code.getText());
                    } else if (format_button.getText().equals("¶")) {
                        format_button.setText("⁋");
                        output.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                        code.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                        output.setText(output.getText());
                        code.setText(code.getText());
                    }
                }
            }
        });

        open_file_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bool_change_format = false;
                if (input_file_name.getText().length() != 0 && !input_file_name.getText().contains(".") &&
                        !input_file_name.getText().contains("/") && !input_file_name.getText().contains("\\") &&
                        !input_file_name.getText().contains(":")) {
                    if (new File(directory + input_file_name.getText() + ".txt").exists()) {
                        boolean bool_open_file = true;
                        if (code.getText().replace(" ", "").replace("　", "").replace(
                                "\n", "").replace("\t", "").length() != 0 &&
                                !code.getText().equals("/ \"Hello, world!\"")) {
                            if (most_recent_file.length() != 0) {
                                try {
                                    File file = new File(most_recent_file);
                                    Scanner file_reader = new Scanner(file);
                                    String code_string = "";
                                    if (file_reader.hasNextLine()) {
                                        code_string = file_reader.nextLine();
                                        while (file_reader.hasNextLine()) {
                                            code_string += "\n" + file_reader.nextLine();
                                        }
                                    }
                                    file_reader.close();
                                    if (!code.getText().equals(code_string)) {
                                        bool_open_file = false;
                                    }
                                } catch (FileNotFoundException a) {
                                    bool_open_file = false;
                                }
                            } else
                                bool_open_file = false;
                        }
                        if (!bool_open_file) {
                            Object[] options = {"Yes　要　Si", "No　不要"};
                            int answer = JOptionPane.showOptionDialog(window,
                                    "Do you want to open a file without saving your program?\n\n" +
                                            "你是否要在不保存程序的情况下打开文件？\n\n" +
                                            "¿Quiere abrir un archivo sin guardar su programa?\n",
                                    "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                                    options[0]);
                            if (answer == JOptionPane.YES_OPTION)
                                bool_open_file = true;
                        }
                        if (bool_open_file) {
                            try {
                                File file = new File(directory + input_file_name.getText() + ".txt");
                                Scanner file_reader = new Scanner(file);
                                String code_string = "";
                                if (file_reader.hasNextLine()) {
                                    code_string = file_reader.nextLine();
                                    while (file_reader.hasNextLine()) {
                                        code_string += "\n" + file_reader.nextLine();
                                    }
                                    if (!code.getText().equals(code_string))
                                        code.setText(code_string);
                                }
                                file_reader.close();
                            } catch (FileNotFoundException a) {
                                JOptionPane.showMessageDialog(window, "“" + input_file_name.getText() +
                                                "” does not exist\n\n“" + input_file_name.getText() + "”不存在\n\n“"
                                                + input_file_name.getText() + "” no existe\n", "",
                                        JOptionPane.ERROR_MESSAGE);
                                bool_open_file = false;
                            }
                            if (bool_open_file)
                                most_recent_file = directory + input_file_name.getText() + ".txt";
                        }
                    } else
                        JOptionPane.showMessageDialog(window, "“" + input_file_name.getText() +
                                "” does not exist\n\n“" + input_file_name.getText() + "”不存在\n\n“" +
                                input_file_name.getText() + "” no existe\n", "", JOptionPane.ERROR_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(window, "Invalid file name\n\n无效的文件名\n\n" +
                            "Nombre de archivo no válido\n", "", JOptionPane.ERROR_MESSAGE);
                bool_change_format = true;
            }
        });

        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bool_change_format = false;
                if (input_file_name.getText().length() != 0 && !input_file_name.getText().contains(".") &&
                        !input_file_name.getText().contains("/") && !input_file_name.getText().contains("\\") &&
                        !input_file_name.getText().contains(":")) {
                    if (code.getText().replace(" ", "").replace("　", "").replace("\n", "").replace("\t", "").length()
                            != 0) {
                        try {
                            boolean bool_save = true;
                            boolean bool_create = false;
                            File file = new File(directory + input_file_name.getText() + ".txt");
                            if (!file.createNewFile()) {
                                Scanner file_reader = new Scanner(file);
                                String code_string = "";
                                if (file_reader.hasNextLine()) {
                                    code_string = file_reader.nextLine();
                                    while (file_reader.hasNextLine()) {
                                        code_string += "\n" + file_reader.nextLine();
                                    }
                                }
                                file_reader.close();
                                if (!code.getText().equals(code_string)) {
                                    Object[] options = {"Yes　要　Si", "No　不要"};
                                    int answer = JOptionPane.showOptionDialog(window,
                                            "Do you want to overwrite “" + input_file_name.getText() + "”?" +
                                                    "\n\n你要覆写“" + input_file_name.getText() + "”吗？\n\n" +
                                                    "¿Quiere sobreescribir “" + input_file_name.getText() + "”?\n", "",
                                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                                            options[0]);
                                    if (answer != JOptionPane.YES_OPTION)
                                        bool_save = false;
                                } else
                                    bool_save = false;
                            } else
                                bool_create = true;
                            if (bool_save) {
                                PrintWriter writer = new PrintWriter(file);
                                writer.write(code.getText());
                                writer.close();
                                if (bool_create)
                                    JOptionPane.showMessageDialog(window, "“" + input_file_name.getText() +
                                            "” has been created\n\n“" + input_file_name.getText() + "”已创建\n\n“"
                                            + input_file_name.getText() + "” se ha creado\n");
                                most_recent_file = directory + input_file_name.getText() + ".txt";
                            }
                        } catch (IOException a) {
                            JOptionPane.showMessageDialog(window, "“" + input_file_name.getText() +
                                    "” does not exist\n\n“" + input_file_name.getText() + "”不存在\n\n“"
                                    + input_file_name.getText() + "” no existe\n", "", JOptionPane.ERROR_MESSAGE);
                        }
                    } else
                        JOptionPane.showMessageDialog(window, "There is no code to save\n\n没有要保存的代码\n\n" +
                                "No hay código para guardar\n", "", JOptionPane.ERROR_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(window, "Invalid file name\n\n无效的文件名\n\n" +
                            "Nombre de archivo no válido\n", "", JOptionPane.ERROR_MESSAGE);
                bool_change_format = true;
            }
        });

        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bool_change_format = false;
                if (input_file_name.getText().length() != 0 && !input_file_name.getText().contains(".") &&
                        !input_file_name.getText().contains("/") && !input_file_name.getText().contains("\\") &&
                        !input_file_name.getText().contains(":")) {
                    File file = new File(directory + input_file_name.getText() + ".txt");
                    if (file.exists()) {
                        Object[] options = {"Yes　要　Si", "No　不要"};
                        int answer = JOptionPane.showOptionDialog(window,
                                "Do you want to delete “" + input_file_name.getText() + "”?" + "\n\n你要删除“" +
                                        input_file_name.getText() + "”吗？\n\n" + "¿Quiere eliminar “" +
                                        input_file_name.getText() + "”?\n", "", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (answer == JOptionPane.YES_OPTION)
                            file.delete();
                    } else {
                        JOptionPane.showMessageDialog(window, "“" + input_file_name.getText() +
                                "” does not exist\n\n“" + input_file_name.getText() + "”不存在\n\n“" +
                                input_file_name.getText() + "” no existe\n", "", JOptionPane.ERROR_MESSAGE);
                    }
                } else
                    JOptionPane.showMessageDialog(window, "Invalid file name\n\n无效的文件名\n\n" +
                            "Nombre de archivo no válido\n", "", JOptionPane.ERROR_MESSAGE);
                bool_change_format = true;
            }
        });

        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                boolean bool_close_window = true;
                if (code.getText().replace(" ", "").replace("　", "").replace(
                        "\n", "").replace("\t", "").length() != 0
                        && !code.getText().equals("/ \"Hello, world!\"")) {
                    if (most_recent_file.length() != 0) {
                        try {
                            File file = new File(most_recent_file);
                            Scanner file_reader = new Scanner(file);
                            String code_string = "";
                            if (file_reader.hasNextLine()) {
                                code_string = file_reader.nextLine();
                                while (file_reader.hasNextLine()) {
                                    code_string += "\n" + file_reader.nextLine();
                                }
                            }
                            file_reader.close();
                            if (!code.getText().equals(code_string)) {
                                bool_close_window = false;
                            }
                        } catch (FileNotFoundException a) {
                            bool_close_window = false;
                        }
                    } else {
                        bool_close_window = false;
                    }
                }
                if (!bool_close_window) {
                    Object[] options = {"Yes　要　Si", "No　不要"};
                    int answer = JOptionPane.showOptionDialog(window,
                            "Do you want to exit without saving your program?\n\n你要退出而不保存程序吗？\n\n" +
                                    "¿Quiere salir sin guardar su programa?\n", "", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (answer == JOptionPane.YES_OPTION)
                        bool_close_window = true;
                }
                if (bool_close_window) {
                    window.dispose();
                    System.exit(0);
                }
            }
        });

        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        window.setVisible(true);
    }
}
