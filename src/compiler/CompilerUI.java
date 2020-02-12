package compiler;

import compiler.lexic.Lexer;
import compiler.output.Output;
import compiler.syntax.Parser;
import compiler.syntax.ParserSym;
import compiler.syntax.symbols.SymbolBase;
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

    private final static String INFO_FILE = "info.txt";
    private final static String TOKENS_FILE = "tokens.txt";
    private final static String ARBOL_FILE = "ArbolSintactico.dot";
    private final static String ERRORS_FILE = "errors.txt";
    private final static String SYMBOLS_FILE = "symbols_table.html";

    private final static String DEFAULT_SOURCE_CODE = "./src/compiler/tests/valid/valid_program3/valid_program3.klt";

    /**
     * UI fields
     */
    private JPanel panelPrincipal;

    private JTabbedPane tabbedPane;

    private JPanel panelCodigo;
    private JPanel panelInformacion;
    private JPanel panelArbol;
    private JPanel panelToken;
    private JPanel panelTabla;
    private JPanel panelErrores;

    private JEditorPane sourceCodeEditor = new JEditorPane();
    private JEditorPane infoEditor = new JEditorPane();
    private JEditorPane tokenEditor = new JEditorPane();
    private JEditorPane arbolEditor = new JEditorPane();
    private JEditorPane tablaEditor = new JEditorPane();
    private JEditorPane errorEditor = new JEditorPane();

    /**
     * UI Menu fields
     */
    private JFileChooser fileChooser = new JFileChooser();
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

    private CompilerUI() throws IOException {

        initUI();

        //Cargar código del test válido 1
        sourceCodeEditor.setText(new String(Files.readAllBytes(Paths.get(DEFAULT_SOURCE_CODE))));
    }

    private void parseSourceCode() throws Exception {

        //Reiniciar tabla de símbolos
        SymbolBase.symbolTable.init();

        Reader sourceCodeReader = new StringReader(sourceCodeEditor.getText());

        int numTokens = 0;

        Output.writeInfo("ANÁLISIS LÉXICO iniciado.");

        Lexer scanner = new Lexer(sourceCodeReader);
        Symbol symbol = scanner.next_token();

        while (symbol.sym != ParserSym.EOF) {
            Output.writeToken(scanner.getRow() + ":" + scanner.getCol() + " TKN_" + ParserSym.terminalNames[symbol.sym] + " [" + symbol.value + "]");
            symbol = scanner.next_token();
            numTokens++;
        }

        Output.writeInfo("Número de tokens identificados: " + numTokens);
        Output.writeInfo("ANÁLISIS LÉXICO terminado.");

        sourceCodeReader.close();
        sourceCodeReader = new StringReader(sourceCodeEditor.getText());
        scanner.yyreset(sourceCodeReader);

        Output.writeInfo("ANÁLISIS SINTÁCTICO iniciado.");

        Parser parser = new Parser(scanner, new ComplexSymbolFactory());
        try {
            parser.parse();
        } catch (Exception e) {
            Output.writeError("Error sintáctico en la posición " + scanner.getRow() + ":" + scanner.getCol() + ". No se puede continuar el análisis");
        }

        Output.writeInfo("ANÁLISIS SINTÁCTICO terminado.");

        Output.writeInfo("Leyendo ficheros de salida.");

        //Leer fichero de info
        infoEditor.setText(new String(Files.readAllBytes(Paths.get(INFO_FILE))));

        //Leer fichero de tokens
        tokenEditor.setText(new String(Files.readAllBytes(Paths.get(TOKENS_FILE))));

        //Leer fichero del árbol sintáctico
        arbolEditor.setText(new String(Files.readAllBytes(Paths.get(ARBOL_FILE))));

        //Leer fichero del simbolos
        tablaEditor.setText(new String(Files.readAllBytes(Paths.get(SYMBOLS_FILE))));

        //Leer fichero de errores
        errorEditor.setText(new String(Files.readAllBytes(Paths.get(ERRORS_FILE))));
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
        setTitle("Kotlite Analyzer");
        setVisible(true);
    }

    /**
     * Inicializar panel de pestañas
     */
    private void initTabPanel() {

        //Panel con pestañas
        tabbedPane = new JTabbedPane();

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

        //Panel de tokens
        panelToken = new JPanel();
        panelToken.setLayout(new BorderLayout());
        panelToken.add(new JScrollPane(tokenEditor));

        //Panel de tokens
        panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());
        panelTabla.add(new JScrollPane(tablaEditor));
        tablaEditor.setContentType("text/html");
        tablaEditor.setEditable(false);

        //Panel de errores
        panelErrores = new JPanel();
        panelErrores.setLayout(new BorderLayout());
        panelErrores.add(new JScrollPane(errorEditor));

        tabbedPane.addTab("  Código Fuente ", panelCodigo);
        tabbedPane.addTab("   Información  ", panelInformacion);
        tabbedPane.addTab("     Errores    ", panelErrores);
        tabbedPane.addTab(" Tabla símbolos ", panelTabla);
        tabbedPane.addTab("Árbol Sintáctico", panelArbol);
        tabbedPane.addTab("     Tokens     ", panelToken);

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
                tabbedPane.setSelectedIndex(1);
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
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    sourceCodeEditor.setText(new String(Files.readAllBytes(Paths.get(path))));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

}
