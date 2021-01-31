import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Lakshayati extends Thread {

    // The memory limit restricts recursion in Lakshayati programs.
    // Feel free to change it according to your computer's storage space.
    public static final int memory_limit = 32767;
    public static boolean bool_continue_program;
    public static Hashtable<String, String> variables = new Hashtable<String, String>();
    public static List<List<String>> list_of_terms = new ArrayList<List<String>>();
    public static List<List<Boolean>> list_of_types = new ArrayList<List<Boolean>>();
    public static JTextArea output = new JTextArea(12, 48);
    public static JButton play_button = new JButton();

    public void run() {
        List<String> line_of_terms = list_of_terms.get(0);
        List<Boolean> parsed_line_of_code = list_of_types.get(0);
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
                    if (string_value != "")
                        variables.put(line_of_terms.get(0), string_value);
                    else
                        variables.remove(line_of_terms.get(0));
                } else {
                    variables.put("/", string_value);
                    output.setText(string_value);
                }
            }
        } else {
            if (parsed_line_of_code.get(0))
                organize_code(line_of_terms.get(0) + " ");
            else if (variables.containsKey(line_of_terms.get(0)))
                organize_code(variables.get(line_of_terms.get(0)) + " ");
        }
        if (bool_continue_program && list_of_types.size() != 0 && list_of_types.size() <= memory_limit) {
            Lakshayati thread = new Lakshayati();
            thread.start();
        } else {
            bool_continue_program = false;
            if (list_of_types.size() > memory_limit)
                output.setText("Error: The recursion limit has been exceeded\n\n错误：已超过递归限制\n\nError: Se superó el límite de recursividad");
            play_button.setText("▶");
        }
    }

    public static void organize_code(String code_string) {
        list_of_terms.add(0, new ArrayList<String>());
        list_of_types.add(0, new ArrayList<Boolean>());
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
                        list_of_terms.add(position_in_list, new ArrayList<String>());
                        list_of_types.add(position_in_list, new ArrayList<Boolean>());
                    }
                    i++;
                } else if (current_character == '"' || current_character == '“' || current_character == '”' || current_character == '„') {
                    bool_outside_string = false;
                    string_category = 1;
                    number_of_quotes = 1;
                    i++;
                } else if (current_character == '\'' || current_character == '‘' || current_character == '’' || current_character == '‚') {
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
                } else if (current_character == '「' || current_character == '『' || current_character == '《' || current_character == '〈') {
                    bool_outside_string = false;
                    string_category = 0;
                    number_of_quotes = 1;
                    quotation_mark = current_character;
                    i++;
                } else if (current_character != '」' && current_character != '』' && current_character != '》' && current_character != '〉') {
                    term = "";
                    while (code_string.charAt(i) != ' ' && code_string.charAt(i) != '　' && code_string.charAt(i) != '\t' && code_string.charAt(i) != '\n') {
                        if (code_string.charAt(i) == '"' || code_string.charAt(i) == '\'' || code_string.charAt(i) == '«' || code_string.charAt(i) == '»' || code_string.charAt(i) == '‹' || code_string.charAt(i) == '›' ||
                                code_string.charAt(i) == '「' || code_string.charAt(i) == '『' || code_string.charAt(i) == '《' || code_string.charAt(i) == '〈' || code_string.charAt(i) == '」' || code_string.charAt(i) == '』' ||
                                code_string.charAt(i) == '》' || code_string.charAt(i) == '〉' || code_string.charAt(i) == '„' || code_string.charAt(i) == '‚' || code_string.charAt(i) == '“' || code_string.charAt(i) == '”' ||
                                code_string.charAt(i) == '‘' || code_string.charAt(i) == '’') {
                            output.setText("Error: Spaces are required to separate strings and variables\n\n错误：需要空格来分隔字符串和变量\n\nError: Se requieren espacios para separar variables y cadenas de caracteres");
                            bool_continue_program = false;
                            break;
                        }
                        term += code_string.charAt(i);
                        i++;
                    }
                    list_of_terms.get(position_in_list).add(term);
                    list_of_types.get(position_in_list).add(false);
                } else {
                    output.setText("Error: " + current_character + " cannot be used to start a string.\n\n错误：" + current_character + "不能用来开始字符串\n\nError: " + current_character + " no se puede utilizar para iniciar una cadena de caracteres.");
                    bool_continue_program = false;
                }
            } else if (string_category != 0) {
                if (bool_analyzing_string) {
                    if ((string_category == 1 && (current_character == '"' || current_character == '“' || current_character == '”' || current_character == '„')) || (string_category == 2 && (current_character == '\'' ||
                            current_character == '‘' || current_character == '’' || current_character == '‚')) || (string_category == 3 && (current_character == '«' || current_character == '»')) || (string_category == 3 &&
                            (current_character == '‹' || current_character == '›'))) {
                        int j = 1;
                        int index_deleted = term.length() - 1;
                        while (code_string.charAt(i-j) == '/' || code_string.charAt(i-j) == '／') {
                            if (j % 2 == 1) {
                                term = term.substring(0, index_deleted) + term.substring(index_deleted + 1, term.length());
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
                        current_character = code_string.charAt(i+1);
                        if (current_character == ' ' || current_character == '　' || current_character == '\t' || current_character == '\n') {
                            list_of_terms.get(position_in_list).add(term);
                            list_of_types.get(position_in_list).add(true);
                            bool_outside_string = true;
                            bool_analyzing_string = false;
                        } else {
                            output.setText("Error: Spaces are required to separate strings and variables\n\n错误：需要空格来分隔字符串和变量\n\nError: Se requieren espacios para separar variables y cadenas de caracteres");
                            bool_continue_program = false;
                        }
                    }
                    i++;
                } else {
                    if ((string_category == 1 && (current_character == '"' || current_character == '“' || current_character == '”' || current_character == '„')) || (string_category == 2 && (current_character == '\'' ||
                            current_character == '‘' || current_character == '’' || current_character == '‚')) || (string_category == 3 && (current_character == '«' || current_character == '»')) || (string_category == 3 &&
                            (current_character == '‹' || current_character == '›'))) {
                        number_of_quotes++;
                        i++;
                    } else if (number_of_quotes % 2 == 1) {
                        bool_analyzing_string = true;
                        end_quotes_needed = number_of_quotes;
                        term = Character.toString(current_character);
                        quote_holder = "";
                        i++;
                    } else {
                        if (current_character == ' ' || current_character == '　' || current_character == '\t' || current_character == '\n') {
                            list_of_terms.get(position_in_list).add("");
                            list_of_types.get(position_in_list).add(true);
                            bool_outside_string = true;
                            bool_analyzing_string = false;
                        } else {
                            output.setText("Error: Spaces are required to separate strings and variables\n\n错误：需要空格来分隔字符串和变量\n\nError: Se requieren espacios para separar variables y cadenas de caracteres");
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
                                term = term.substring(0, index_deleted) + term.substring(index_deleted + 1, term.length());
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
                        if (current_character == ' ' || current_character == '　' || current_character == '\t' || current_character == '\n') {
                            list_of_terms.get(position_in_list).add(term);
                            list_of_types.get(position_in_list).add(true);
                            bool_outside_string = true;
                            bool_analyzing_string = false;
                        } else {
                            output.setText("Error: Spaces are required to separate strings and variables\n\n错误：需要空格来分隔字符串和变量\n\nError: Se requieren espacios para separar variables y cadenas de caracteres");
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
                                        code_string = code_string.substring(0, index_deleted) + code_string.substring(index_deleted + 1, code_string.length());
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
            output.setText("Error: A string has not been terminated\n\n错误：字符串尚未终止\n\nError: No se ha minado una cadena de caracteres");
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
        JScrollPane output_scroll = new JScrollPane(output);
        output_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane code_scroll = new JScrollPane(code);
        code_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        output.setEditable(false);
        play_button.setText("▶");
        format_button.setText("⁋");
        open_file_button.setText("Open　打开　Abrir");
        save_button.setText("Save　保存　Guardar");
        play_button.setFont(new Font("Courier", Font.PLAIN, 18));
        format_button.setFont(new Font("Courier", Font.PLAIN, 21));
        open_file_button.setFont(new Font("Arial Unicode MS", Font.PLAIN, 15));
        save_button.setFont(new Font("Arial Unicode MS", Font.PLAIN, 15));
        code.setText("/ \"Hello, world!\"");
        output.setText("Hello, world!");
        code.setFont(new Font("Courier", Font.PLAIN, 18));
        output.setFont(new Font("Courier", Font.PLAIN, 18));
        window.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(24, 24, 24, 24);
        c.ipady = 4;
        c.gridx = 0;
        c.gridy = 0;
        window.add(play_button, c);
        c.gridx = 1;
        c.gridy = 0;
        window.add(format_button, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        window.add(code_scroll, c);
        c.gridx = 0;
        c.gridy = 2;
        window.add(output_scroll, c);
        window.pack();
        Dimension size = window.getSize();
        window.setSize(size.width, size.height + 36);
        window.setLocationRelativeTo(null);

        play_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!bool_continue_program) {
                    if (code.getText().replace(" ", "").replace("　", "").replace("\n", "").replace("\t", "").length() != 0) {
                        output.setText("");
                        bool_continue_program = true;
                        list_of_terms.clear();
                        list_of_types.clear();
                        variables.clear();
                        variables.put("/", "");
                        organize_code(code.getText() + " ");
                        if (bool_continue_program && list_of_types.size() != 0 && list_of_types.size() <= memory_limit) {
                            play_button.setText("■");
                            Lakshayati thread = new Lakshayati();
                            thread.start();
                        }
                        else {
                            bool_continue_program = false;
                            if (list_of_types.size() > memory_limit)
                                output.setText("Error: Code is too long\n\n错误：代码太长\n\nError: El código es demasiado largo");
                        }
                    } else output.setText("");
                } else {
                    bool_continue_program = false;
                    play_button.setText("▶");
                }
            }
        });

        format_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (format_button.getText() == "⁋") {
                    format_button.setText("¶");
                    output.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    code.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                } else {
                    format_button.setText("⁋");
                    output.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    code.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                }
            }
        });

        window.setVisible(true);
    }
}
