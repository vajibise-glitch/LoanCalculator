import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoanCalculator extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creating GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Title Label
        Label title = new Label("Loan Payment Calculator");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        grid.add(title, 0, 0, 2, 1);

        // Loan Amount
        Label loanLabel = new Label("Loan Amount:");
        TextField loanField = new TextField();
        grid.add(loanLabel, 0, 1);
        grid.add(loanField, 1, 1);

        // Annual Interest Rate
        Label rateLabel = new Label("Annual Interest Rate:");
        TextField rateField = new TextField();
        grid.add(rateLabel, 0, 2);
        grid.add(rateField, 1, 2);

        // Number of Years
        Label yearsLabel = new Label("Number of Years:");
        TextField yearsField = new TextField();
        grid.add(yearsLabel, 0, 3);
        grid.add(yearsField, 1, 3);

        // Output Labels
        Label monthlyPaymentLabel = new Label("Monthly Payment:");
        Label totalPaymentLabel = new Label("Total Payment:");
        grid.add(monthlyPaymentLabel, 0, 5);
        grid.add(totalPaymentLabel, 0, 6);

        Label monthlyPaymentOutput = new Label();
        Label totalPaymentOutput = new Label();
        grid.add(monthlyPaymentOutput, 1, 5);
        grid.add(totalPaymentOutput, 1, 6);

        // Buttons
        Button calculateButton = new Button("Compute Payment");
        Button resetButton = new Button("Reset");
        grid.add(calculateButton, 0, 4);
        grid.add(resetButton, 1, 4);

        // Event Handling
        calculateButton.setOnAction(e -> {
            try {
                double loanAmount = Double.parseDouble(loanField.getText());
                double annualRate = Double.parseDouble(rateField.getText());
                int years = Integer.parseInt(yearsField.getText());

                Loan loan = new Loan(annualRate, years, loanAmount);

                // Display Results
                monthlyPaymentOutput.setText(String.format("$%.2f", loan.getMonthlyPayment()));
                totalPaymentOutput.setText(String.format("$%.2f", loan.getTotalPayment()));
            } catch (NumberFormatException ex) {
                monthlyPaymentOutput.setText("Invalid Input");
                totalPaymentOutput.setText("Invalid Input");
            }
        });

        resetButton.setOnAction(e -> {
            loanField.clear();
            rateField.clear();
            yearsField.clear();
            monthlyPaymentOutput.setText("");
            totalPaymentOutput.setText("");
        });

        // Set Scene
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setTitle("Loan Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Loan Class
    public static class Loan {
        private double annualInterestRate;
        private int numberOfYears;
        private double loanAmount;

        public Loan(double annualInterestRate, int numberOfYears, double loanAmount) {
            this.annualInterestRate = annualInterestRate;
            this.numberOfYears = numberOfYears;
            this.loanAmount = loanAmount;
        }

        public double getMonthlyPayment() {
            double monthlyInterestRate = annualInterestRate / 1200;
            return loanAmount * monthlyInterestRate / (1 - (1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12)));
        }

        public double getTotalPayment() {
            return getMonthlyPayment() * numberOfYears * 12;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}