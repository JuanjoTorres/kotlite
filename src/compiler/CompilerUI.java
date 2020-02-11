package compiler;

import compiler.lexic.Lexer;
import compiler.syntax.Parser;
import compiler.syntax.ParserSym;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CompilerUI extends JFrame {

    /**
     * Constant fields
     */
    private static final int ANCHO = 1024;
    private static final int ALTO = 800;

    private final static String TOKENS_FILE = "tokens.txt";

    private final static String DEFAULT_SOURCE_CODE = "./src/compiler/tests/valid/valid_program3/valid_program3.klt";

    /**
     * UI fields
     */
    private JPanel panelPrincipal;
    private JPanel panelCodigo;
    private JPanel panelInformacion;
    private JPanel panelArbol;
    private JPanel panelErrores;

    JEditorPane sourceCodeEditor = new JEditorPane();
    JEditorPane infoEditor = new JEditorPane();
    JEditorPane arbolEditor = new JEditorPane();
    JEditorPane errorEditor = new JEditorPane();

    /**
     * UI Menu fields
     */
    JFileChooser fileChooser = new JFileChooser();
    private JButton chooseFile = new JButton();
    private JButton resolver = new JButton();
    private JLabel labelMenu = new JLabel();

    /**
     * main()
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            new CompilerUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CompilerUI() throws Exception {

        initUI();

        //Cargar código del test válido 1
        loadSourceCodeFile(DEFAULT_SOURCE_CODE);
    }

    private void loadSourceCodeFile(String path) throws IOException {
        sourceCodeEditor.setText(new String(Files.readAllBytes(Paths.get(path))));
    }

    private void parseSourceCode() throws Exception {

        Reader sourceCodeReader = new StringReader(sourceCodeEditor.getText());

        FileWriter fileWriter = new FileWriter(TOKENS_FILE);

        int numTokens = 0;
        Lexer scanner = new Lexer(sourceCodeReader);
        Symbol symbol = scanner.next_token();

        System.out.println("FASE LEXICA iniciada.");
        System.out.println("Generando fichero de tokens...");

        while (symbol.sym != ParserSym.EOF) {

            fileWriter.write(scanner.getRow() + ":" + scanner.getCol()    // Posicion donde se ha encontrado el token
                    + " TKN_" + ParserSym.terminalNames[symbol.sym]     // Tipo de token encontrado
                    + " [" + symbol.value + "]\n");                     // Valor del token

            symbol = scanner.next_token();
            numTokens++;
        }

        sourceCodeReader.close();
        fileWriter.close();
        scanner.yyclose();

        System.out.println("Número de tokens identificados: " + numTokens);
        System.out.println("FASE LEXICA terminada.");

        sourceCodeReader = new StringReader(sourceCodeEditor.getText());
        scanner.yyreset(sourceCodeReader);

        ComplexSymbolFactory factory = new ComplexSymbolFactory();
        Parser parser = new Parser(scanner, factory);
        parser.debug_parse();
    }

    private void initUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Border Layout para poner el menú en la parte de arriba
        panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setFocusable(true);

        initTabPanel();
        initMenu();
        initMenuActions();

        setLocationByPlatform(true);
        setContentPane(panelPrincipal);
        setSize(new Dimension(ANCHO, ALTO));
        setMinimumSize(new Dimension(ANCHO, ALTO));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Kotlite IDE");
        setVisible(true);
    }

    /**
     * Inicializar panel de pestañas
     */
    private void initTabPanel() {

        //Panel con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        //Panel de código fuente
        panelCodigo = new JPanel();
        panelCodigo.setLayout(new BorderLayout());
        panelCodigo.add(new JScrollPane(sourceCodeEditor));

        //Panel de informacion
        panelInformacion = new JPanel();
        panelInformacion.setLayout(new BorderLayout());
        panelInformacion.add(new JScrollPane(infoEditor));

        //Panel de árbol sintáctico
        panelArbol = new JPanel();
        panelArbol.setLayout(new BorderLayout());
        panelArbol.add(new JScrollPane(arbolEditor));

        //Panel de errores
        panelErrores = new JPanel();
        panelErrores.setLayout(new BorderLayout());
        panelErrores.add(new JScrollPane(errorEditor));

        tabbedPane.addTab("  Código Fuente ", panelCodigo);
        tabbedPane.addTab("   Información  ", panelInformacion);
        tabbedPane.addTab("Árbol Sintáctico", panelArbol);
        tabbedPane.addTab("     Errores    ", panelErrores);

        //Add the tabbed pane to this panel.
        panelPrincipal.add(tabbedPane);
    }

    /**
     * Inicilizar UI del menu
     */
    private void initMenu() {

        JToolBar menu = new JToolBar();
        menu.setFloatable(false);
        panelPrincipal.add(menu, BorderLayout.PAGE_START);

        chooseFile.setText("Seleccionar fichero de código fuente");
        menu.add(chooseFile);

        resolver.setText("Analizar");

        menu.add(labelMenu);

        menu.addSeparator();
        menu.add(Box.createHorizontalGlue());
        menu.add(resolver);
    }

    /**
     * Inicilizar acciones del menu
     */
    private void initMenuActions() {

        resolver.addActionListener(e -> {
            try {
                parseSourceCode();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        chooseFile.addActionListener(e -> {

            //Poner directorio de tests como predeterminado
            fileChooser.setCurrentDirectory(new File("./src/compiler/tests/"));

            if (fileChooser.showOpenDialog(panelPrincipal) == JFileChooser.APPROVE_OPTION) {
                try {
                    loadSourceCodeFile(fileChooser.getSelectedFile().getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

}
