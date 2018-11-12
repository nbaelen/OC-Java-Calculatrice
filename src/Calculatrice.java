import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Réalisé par Nathan Baelen dans le cadre d'un TP OpenClassrooms
 */

public class Calculatrice extends JFrame {

    //Éléments graphiques
    private JPanel mainContainer = new JPanel();
    private JPanel operateursContainer = new JPanel();
    private JPanel chiffresContainer = new JPanel();
    private JPanel ecranContainer = new JPanel();
    JLabel ecran = new JLabel("0");

    //Tableaux contenant l'ensemble des boutons
    private JButton[] allOperateursButtons = new JButton[5];
    private JButton[] allChiffresButtons = new JButton[12];

    //Variables utiles au calcul
    private String operateur;
    private Double previousNumber = 0.0;
    private Boolean inProgress = false;
    private Boolean alreadyCalculated = false;

    /**
     * Constructeur de classe en charge de la création de la fenêtre d'affichage, pas de l'organisation de son contenu
     * La fenêtre n'est pas redimensionnable par défaut
     */
    public Calculatrice() {
        this.setTitle("Calculatrice");
        this.setSize(260, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setGlobalLayout();
        this.setVisible(true);
    }

    public void setOperateur(String operateur) {
        this.operateur = operateur;
    }
    
    /*
        Structure de la fenetre
            - un JPanel North avec le JLabel d'affichage
            - un JPanel East avec les 4 boutons d'opérateurs
            - un JPanel Center avec les 10 boutons de chiffres + le décimal + le égal
     */

    /**
     * Fonction en charge du layout global de la calculatrice
     */
    public void setGlobalLayout() {
        this.mainContainer.setLayout(new BorderLayout());
        this.setEcranLayout();
        this.setOperateursLayout();
        this.setChiffresLayout();
        this.setContentPane(mainContainer);
    }

    /**
     * Fonction en charge du layout de l'écran
     */
    public void setEcranLayout() {
        this.ecran.setHorizontalAlignment(JLabel.RIGHT);
        this.ecran.setPreferredSize(new Dimension(250, 30));
        this.ecran.setFont(new Font("Tahoma", Font.BOLD, 20));
        this.ecran.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.ecranContainer.setPreferredSize(new Dimension(260, 40));
        this.ecranContainer.add(ecran);
        this.mainContainer.add(this.ecranContainer, BorderLayout.NORTH);
    }

    /**
     * Fonction en charge du layout des opérateurs
     */
    public void setOperateursLayout() {
        String[] operateurs = {"C", "+", "-", "*", "/"};
        this.operateursContainer.setLayout(new GridLayout(6,1));
        for (int i=0; i < operateurs.length; i++) {
            this.allOperateursButtons[i] = new JButton(operateurs[i]);
            this.allOperateursButtons[i].addActionListener(new OperateurListener());
            this.operateursContainer.add(this.allOperateursButtons[i]);
        }
        this.mainContainer.add(this.operateursContainer, BorderLayout.EAST);
    }

    /**
     * Fonction en charge du layout des chiffres
     */
    public void setChiffresLayout() {
        String[] chiffres = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "="};
        this.chiffresContainer.setLayout(new GridLayout(4,3));
        for (int i=0; i < chiffres.length; i++) {
            this.allChiffresButtons[i] = new JButton(chiffres[i]);
            this.allChiffresButtons[i].addActionListener(new ChiffreListener());
            this.chiffresContainer.add(this.allChiffresButtons[i]);

        }
        this.mainContainer.add(this.chiffresContainer, BorderLayout.CENTER);
    }

    /*
        ActionListener divisés en deux ActionListener différents
     */

    /**
     * ActionListener adapté pour les boutons opérateurs
     */
    public class OperateurListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (((JButton)e.getSource()).getText()) {
                case "C" :
                    ecran.setText("0");
                    operateur = "";
                    previousNumber = 0.0;
                    inProgress = false;
                    alreadyCalculated = false;
                    break;
                default :
                    inProgress = true;
                    if (previousNumber == 0.0) {
                        previousNumber = Double.parseDouble(ecran.getText());
                    } else {
                        if (!alreadyCalculated) {
                            calculate();
                            alreadyCalculated = false;
                        }
                        previousNumber = Double.parseDouble(ecran.getText());
                    }
                    operateur = ((JButton)e.getSource()).getText();
                    break;
            }
        }
    }

    /**
     * ActionListener adapté pour les boutons chiffres
     */
    public class ChiffreListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String chiffre = ((JButton)e.getSource()).getText();
            switch (chiffre) {
                case "=" :
                    calculate();
                    previousNumber = Double.parseDouble(ecran.getText());
                    alreadyCalculated = true;
                    break;
                case "." :
                    ecran.setText(ecran.getText() + chiffre);
                    break;
                default :
                    if (ecran.getText() == "0") {
                        ecran.setText(chiffre);
                        inProgress = false;
                    }
                    else {
                        if (inProgress) {
                            inProgress = false;
                            ecran.setText(chiffre);
                        } else {
                            ecran.setText(ecran.getText() + chiffre);
                        }
                    }
                    break;
            }
        }
    }

    /*
        Fonctions pour la partie "calcul" à proprement dit
     */

    public void calculate() {
        Double calcul = 0.0;
        switch (operateur) {
            case "+" :
                calcul = this.previousNumber+Double.parseDouble(ecran.getText());
                break;
            case "-" :
                calcul = this.previousNumber-Double.parseDouble(ecran.getText());
                break;
            case "*" :
                calcul = this.previousNumber*Double.parseDouble(ecran.getText());
                break;
            case "/" :
                calcul = this.previousNumber/Double.parseDouble(ecran.getText());
                break;
        }
        ecran.setText(Double.toString(calcul));
    }
}

























