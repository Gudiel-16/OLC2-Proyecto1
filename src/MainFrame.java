import Entorno.Entorno;
import Gramatica.GramaticaLexer;
import Gramatica.GramaticaParser;
import Utilidades.Consola;
import Utilidades.Errores;
import Utilidades.ErroresLexSint;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;
import ArbolAST.Ast;
import ArbolAST.Nodo;

import static org.antlr.v4.runtime.CharStreams.fromFileName;
import static org.antlr.v4.runtime.CharStreams.fromString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Element;
import javax.swing.JFileChooser;
import java.io.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private static JTextArea txtCodigo;
    private static JTextArea lines;
    private JScrollPane jsp;


    public MainFrame(){
        setTitle("PROYECTO 1");
        setBounds(300,50,1000,750);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //MENU
        JMenuBar mibarra = new JMenuBar();
        mibarra.setBounds(5, 35, 500, 200);
        JMenu analizar = new JMenu("Analizar");
        JMenu edicion = new JMenu("Edicion");
        JMenu reportes = new JMenu("Reportes");
        JMenuItem analiza = new JMenuItem("Analizar");
        JMenuItem c3d = new JMenuItem("C3D");
        JMenuItem abrir = new JMenuItem("Abrir");
        JMenuItem limpiar = new JMenuItem("Limpiar");
        JMenuItem reporteTablaSimbolo = new JMenuItem("Tabla de Simbolos");
        JMenuItem reporteCST= new JMenuItem("Arbol CST");
        JMenuItem reporteAST = new JMenuItem("Arbol AST");
        JMenuItem reporteErrores = new JMenuItem("Tabla de Errores");
        JMenuItem reporteGDA = new JMenuItem("Grafo GDA");
        analizar.add(analiza);
        analizar.add(c3d);
        edicion.add(abrir);
        edicion.add(limpiar);
        reportes.add(reporteTablaSimbolo);
        reportes.add(reporteCST);
        reportes.add(reporteAST);
        reportes.add(reporteErrores);
        reportes.add(reporteGDA);
        mibarra.add(analizar);
        mibarra.add(edicion);
        mibarra.add(reportes);
        add(mibarra, BorderLayout.NORTH);

        //TXT CODIGO
        miTxtCodigo();

        //TXT CONSOLA
        JTextArea txtConsola= new JTextArea(15, 85);
        JScrollPane srcConsola = new JScrollPane(txtConsola, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //txtConsola.setBounds(5, 35, 385, 330);
        //txtConsola.setLineWrap(true);
        //txtConsola.setWrapStyleWord(true);
        //srcConsola.setBounds(20, 500, 700, 200);
        //add(srcConsola, BorderLayout.AFTER_LAST_LINE);

        //3D
        JTextArea txt3D= new JTextArea(15, 85);
        JScrollPane src3d = new JScrollPane(txt3D, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //ERRORES
        JTextArea txtErrores= new JTextArea(15, 85);
        JScrollPane srcErrores = new JScrollPane(txtErrores, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel jpConsola = new JPanel();
        JPanel jp3D = new JPanel();
        JPanel jpErrores = new JPanel();
        jpConsola.add(srcConsola);
        jp3D.add(src3d);
        jpErrores.add(srcErrores);

        JTabbedPane tabs = new JTabbedPane();
        //tabs.setBounds(50,50,800,200);
        tabs.addTab("CONSOLA",jpConsola);
        tabs.addTab("3D",jp3D);
        tabs.add("ERRORES",jpErrores);

        add(tabs,BorderLayout.AFTER_LAST_LINE);

        setVisible(true);

        //EVENTOS
        analiza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                txtConsola.setText("");
                txtErrores.setText("");
                txt3D.setText("");

                ArrayList<ErroresLexSint> erroresLexicos= new ArrayList<ErroresLexSint>();
                ArrayList<ErroresLexSint> erroresSintacticos= new ArrayList<ErroresLexSint>();

                CharStream cs = fromString(txtCodigo.getText());
                GramaticaLexer lexico = new GramaticaLexer(cs);
                lexico.removeErrorListeners();
                lexico.addErrorListener(new BaseErrorListener(){
                    @Override
                    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
                    {
                        erroresLexicos.add(new ErroresLexSint(line, charPositionInLine, msg, ErroresLexSint.ErrorTipo.Lexico));
                    }
                });

                CommonTokenStream tokens = new CommonTokenStream(lexico);
                GramaticaParser sintactico = new GramaticaParser(tokens);
                sintactico.removeErrorListeners();
                sintactico.addErrorListener(new BaseErrorListener(){
                    @Override
                    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
                    {
                        erroresSintacticos.add(new ErroresLexSint(line, charPositionInLine, msg, ErroresLexSint.ErrorTipo.Sintactico));
                    }
                });
                GramaticaParser.ParseContext startCtx = sintactico.parse();

                //TODO: validando si hay errores lexicos o sintactivos
                if(erroresLexicos.size() > 0 || erroresSintacticos.size() > 0){
                    String errLS = "";
                    String errorL = "";
                    for (ErroresLexSint errL: erroresLexicos)
                        errorL += "Linea: " + errL.fila + " Columna: " + errL.columna + " Descripcion: " +errL.descripcion + " Tipo: " + errL.tipo + "\n";

                    String errorS = "";
                    for (ErroresLexSint errS: erroresSintacticos)
                        errorS += "Linea: " + errS.fila + " Columna: " + errS.columna + " Descripcion: " + errS.descripcion + " Tipo: " + errS.tipo + "\n";

                    errLS = errorL + errorS;
                    txtErrores.setText(errLS);

                    //TODO: Reporte errores, mostrara solo lexicos y sintactivos
                    String errorLex = "";
                    for (ErroresLexSint errL: erroresLexicos)
                        errorLex += errL.toString();

                    String errorSint = "";
                    for (ErroresLexSint errS: erroresSintacticos)
                        errorSint += errS.toString();

                    //TODO: CST
                    List<String> rulesName = Arrays.asList(sintactico.getRuleNames());
                    TreeViewer viewr = new TreeViewer(rulesName,startCtx);
                    viewr.open();

                    reporteErrores(errorLex, errorSint, "");
                }else{
                    //TODO: ejecutando interprete fortran
                    Entorno ent = new Entorno(null);
                    Visitor visitor = new Visitor(ent);
                    visitor.visit(startCtx);

                    //TODO: ejecutando AST
                    VisitorAST visitorAST = new VisitorAST();
                    visitorAST.miArbol.LimpiarListaDot();
                    visitorAST.visit(startCtx);
                    visitorAST.miArbol.CrearTextoDot(visitorAST.nodo);

                    //TODO: Mostrando resultados de consola
                    txtConsola.setText(Consola.getConsola());

                    //TODO: Mostrando errores
                    txtErrores.setText(Errores.getErrores());

                    //TODO: Reportes: mostrar solo errores semanticos y tabla de simbolos
                    reporteErrores("", "", Errores.geteErroresSemanticos());
                    reporteSimbolos(ent.getReporteSimbolos());

                    //TODO: AST
                    try {
                        generarDotAST(visitorAST.miArbol.GetTextoDot());
                    }catch (Exception exec){
                        System.out.println("Error al generar dot AST :( "+ exec.getMessage());
                    }

                    //TODO: CST
                    List<String> rulesName = Arrays.asList(sintactico.getRuleNames());
                    TreeViewer viewr = new TreeViewer(rulesName,startCtx);
                    viewr.open();

                    //TODO: Limpiando datos
                    Consola.clearPilaConsola();
                    Errores.clearPilaErrores();
                }

                System.out.println("SUCCESS");
            }
        });

        c3d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CharStream cs = fromString(txtCodigo.getText());
                GramaticaLexer lexico = new GramaticaLexer(cs);

                CommonTokenStream tokens = new CommonTokenStream(lexico);
                GramaticaParser sintactico = new GramaticaParser(tokens);

                GramaticaParser.ParseContext startCtx = sintactico.parse();

                VisitorCtresD vc3d = new VisitorCtresD(new Entorno(null));
                vc3d.visit(startCtx);

                String salida = "";
                salida += vc3d.listC3D.getHeader();
                salida += vc3d.listC3D.getSubrFunciones();
                for (String ln: vc3d.listC3D.codigo3d)
                    salida += ln + "\n";
                txt3D.setText(salida);
                //System.out.println(Consola.getConsola());
                //System.out.println(Errores.getErrores());

                Consola.clearPilaConsola();
                Errores.clearPilaErrores();
            }
        });

        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aux="";
                String texto="";
                try {
                    JFileChooser file=new JFileChooser();
                    FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.f90", "f90");
                    file.setFileFilter(filtro);
                    file.showOpenDialog(txtCodigo);
                    /**abrimos el archivo seleccionado*/
                    File abre=file.getSelectedFile();

                    if(abre!=null)
                    {
                        FileReader archivos=new FileReader(abre);
                        BufferedReader lee=new BufferedReader(archivos);
                        while((aux=lee.readLine())!=null)
                        {
                            texto+= aux+ "\n";
                        }
                        lee.close();
                    }

                }catch (Exception ex){
                    System.out.println(ex);
                }
                txtCodigo.setText(texto);
            }
        });

        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCodigo.setText("");
                txtConsola.setText("");
                txt3D.setText("");
                txtErrores.setText("");
            }
        });

        reporteTablaSimbolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Reportes tabla simbolo");

            }
        });

        reporteCST.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reportes CST");
            }
        });

        reporteAST.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reportes AST");
            }
        });

        reporteErrores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reportes errores");
            }
        });

        reporteGDA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reportes GDA");
            }
        });
    }

    public void miTxtCodigo(){
        jsp = new JScrollPane();
        txtCodigo = new JTextArea();
        Font fuente = new Font("Dialog", Font.PLAIN, 15);
        txtCodigo.setFont(fuente);
        lines = new JTextArea("1");
        lines.setBackground(Color.LIGHT_GRAY);
        lines.setFont(fuente);
        lines.setEditable(false);
        //  Code to implement line numbers inside the JTextArea
        txtCodigo.getDocument().addDocumentListener(new DocumentListener() {
            public String getText() {
                int caretPosition = txtCodigo.getDocument().getLength();
                Element root = txtCodigo.getDocument().getDefaultRootElement();
                String text = "1" + System.getProperty("line.separator");
                for(int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
                    text += i + System.getProperty("line.separator");
                }
                return text;
            }
            @Override
            public void changedUpdate(DocumentEvent de) {
                lines.setText(getText());
            }
            @Override
            public void insertUpdate(DocumentEvent de) {
                lines.setText(getText());
            }
            @Override
            public void removeUpdate(DocumentEvent de) {
                lines.setText(getText());
            }
        });
        jsp.getViewport().add(txtCodigo);
        jsp.setRowHeaderView(lines);
        add(jsp);
    }

    void reporteErrores(String errorLexico, String errorSintactico, String errorSemantico){
        FileWriter filewriter = null;
        PrintWriter printw = null;

        try{
            filewriter = new FileWriter("reporte_errores.html");
            printw = new PrintWriter(filewriter);

            printw.println("<html>");
            printw.println("<head><title>ERRORES</title></head>");
            printw.println("<body bgcolor=white>");

            //TODO errroes lexicos
            printw.println("<h2><center>ERRORES LEXICOS</center></h2>");

            printw.println("<center><table class=\"egt\" border=\"10\">");

            printw.println("<tr bgcolor=\"#00FFFF\">");
            printw.println("<th>"+"LINEA"+"</th>");
            printw.println("<th>"+"COLUMNA"+"</th>");
            printw.println("<th>"+"DESCRIPCION"+"</th>");
            printw.println("<th>"+"TIPO"+"</th>");
            printw.println("</tr>");

            printw.println(errorLexico);

            printw.println("</table></center>");

            //TODO: errores sintacticos
            printw.println("<h2><center>ERRORES SINTACTICOS</center></h2>");

            printw.println("<center><table class=\"egt\" border=\"10\">");

            printw.println("<tr bgcolor=\"#00FFFF\">");
            printw.println("<th>"+"LINEA"+"</th>");
            printw.println("<th>"+"COLUMNA"+"</th>");
            printw.println("<th>"+"DESCRIPCION"+"</th>");
            printw.println("<th>"+"TIPO"+"</th>");
            printw.println("</tr>");

            printw.println(errorSintactico);

            printw.println("</table></center>");


            //TODO: errores semanticos
            printw.println("<center><table class=\"egt\" border=\"10\">");

            printw.println("<tr bgcolor=\"#00FFFF\">");
            printw.println("<th>"+"ERRORES SEMANTICOS"+"</th>");
            printw.println("</tr>");

            printw.println("<h2><center>ERRORES SEMANTICOS</center></h2>");
            printw.println("<tr>");
            printw.println("<td>"+errorSemantico+"</td>");
            printw.println("</tr>");

            printw.println("</table></center>");

            printw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    void reporteSimbolos(String c){
        FileWriter filewriter = null;
        PrintWriter printw = null;

        try{
            filewriter = new FileWriter("reporte_simbolos.html");
            printw = new PrintWriter(filewriter);

            printw.println("<html>");
            printw.println("<head><title>REPORTE DE SIMBOLOS</title></head>");
            printw.println("<body bgcolor=white>");

            printw.println("<center><table class=\"egt\" border=\"10\">");

            printw.println("<tr bgcolor=\"#00FFFF\">");
            printw.println("<th>"+"IDENTIFICADOR"+"</th>");
            printw.println("<th>"+"VALOR"+"</th>");
            printw.println("<th>"+"TIPO"+"</th>");
            printw.println("<th>"+"TIPO SIMBOLO"+"</th>");
            printw.println("</tr>");

            printw.println("<h2><center>SIMBOLOS</center></h2>");
            printw.println(c);

            printw.println("</table></center>");

            printw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void generarDotAST(String rast) throws IOException {
        String reportAst = "digraph G { \n"+
                "Parse10000[label = \"Parse\"];\n";
        reportAst += rast;
        reportAst+="}";

        FileWriter dotAST = new FileWriter("arbol_ast.dot");
        dotAST.write(reportAst);
        dotAST.close();
        Runtime.getRuntime().exec("dot -Tpdf arbol_ast.dot -o arbol_ast.pdf");
    }

    public static void main(String[] args) throws IOException {

//        ArrayList<ErroresLexSint> erroresLexicos= new ArrayList<ErroresLexSint>();
//        ArrayList<ErroresLexSint> erroresSintacticos= new ArrayList<ErroresLexSint>();
//
//        CharStream cs = fromFileName("test.txt");
//        GramaticaLexer lexico = new GramaticaLexer(cs);
//        lexico.removeErrorListeners();
//        lexico.addErrorListener(new BaseErrorListener(){
//            @Override
//            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
//            {
//                erroresLexicos.add(new ErroresLexSint(line, charPositionInLine, msg, ErroresLexSint.ErrorTipo.Lexico));
//            }
//        });
//
//        CommonTokenStream tokens = new CommonTokenStream(lexico);
//        GramaticaParser sintactico = new GramaticaParser(tokens);
//        sintactico.removeErrorListeners();
//        sintactico.addErrorListener(new BaseErrorListener(){
//            @Override
//            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
//            {
//                erroresSintacticos.add(new ErroresLexSint(line, charPositionInLine, msg, ErroresLexSint.ErrorTipo.Sintactico));
//            }
//        });
//        GramaticaParser.ParseContext startCtx = sintactico.parse();
//
//        Visitor visitor = new Visitor(new Entorno(null));
//        visitor.visit(startCtx);

//        VisitorAST visitorAST = new VisitorAST();
//        visitorAST.miArbol.LimpiarListaDot();
//        visitorAST.visit(startCtx);
//        //visitorAST.miArbol.raiz.verNodo();
//        System.out.println("Parse10000[label = \"Parse\"];");
//        visitorAST.miArbol.PrintReporte(visitorAST.nodo);
//
//        //TODO: Mostrando resultados de consola
//        //System.out.println(Consola.getConsola());
//
//        //TODO: Mostrando errores
//        System.out.println("-----");
//          System.out.println(erroresSintacticos.size() + " <><>");
//        System.out.println("-----");
//
//        //TODO: Limpiando datos
//        //Consola.clearPilaConsola();
//        //Errores.clearPilaErrores();
//
//        //TODO: AST
//        String reportAst = "digraph G { \n"+
//                            "Parse10000[label = \"Parse\"];\n";
//        reportAst += visitorAST.miArbol.GetTextoDot();
//        reportAst+="}";
//
//        FileWriter dotAST = new FileWriter("arbol_ast.dot");
//        dotAST.write(reportAst);
//        dotAST.close();
//        Runtime.getRuntime().exec("dot -Tpdf arbol_ast.dot -o arbol_ast.pdf");
//
//        System.out.println("SUCCESS");

//        CharStream cs = fromFileName("test.txt");
//        GramaticaLexer lexico = new GramaticaLexer(cs);
//
//        CommonTokenStream tokens = new CommonTokenStream(lexico);
//        GramaticaParser sintactico = new GramaticaParser(tokens);
//        GramaticaParser.ParseContext startCtx = sintactico.parse();
//
//        VisitorCtresD vc3d = new VisitorCtresD(new Entorno(null));
//        vc3d.visit(startCtx);

//        Visitor visitor = new Visitor(new Entorno(null));
//        visitor.visit(startCtx);

//        System.out.println(vc3d.listC3D.getHeader());
//        System.out.println(vc3d.listC3D.getSubrFunciones());
//        for (String ln: vc3d.listC3D.codigo3d)
//            System.out.println(ln);
//        System.out.println("-------------------");
//        System.out.println(Consola.getConsola());
//        System.out.println(Errores.getErrores());
//
//        FileWriter file3d = new FileWriter("salida.c");
//        file3d.write(vc3d.listC3D.getHeader());
//        file3d.write(vc3d.listC3D.getSubrFunciones());
//        for (String ln: vc3d.listC3D.codigo3d)
//            file3d.write(ln + "\n");
//        file3d.close();

        MainFrame mainFrame = new MainFrame();
    }
}
