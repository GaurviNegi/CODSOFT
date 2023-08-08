import java.io.*;
import java.util.*;

//ACCOUNT CLASS
class AccountClass {
    Scanner sc= new Scanner(System.in);
    static int ac_count=1000;
    String accHolderName;
    String account_id;
    int pin;
    int account_no;
    double balance;

    //functions for creating accounts
    void createAccount()  {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Create account");
        {
            System.out.println("enter account holder name");
            this.accHolderName = sc.nextLine();

            System.out.println("set unique account-id");
            this.account_id = sc.nextLine();

            //condition for pin length checking.....................
            while (String.valueOf(pin).length() != 4) {
                System.out.println("set 4 digit pin");
                this.pin = sc.nextInt();
            }

            System.out.println("deposit initial balance");
            balance = sc.nextInt();
            this.account_no = ++ac_count;
        }

        System.out.println("account successfully created");
        System.out.print("your account details are: ");
        System.out.println("\nAccount-holder: "+this.accHolderName+"\nAccount-number: "+this.account_no+"\nAccount" +
                "-balance: "+this.balance);
        System.out.println("Thank you");


        ATMClass.list.add(this);

        //writing into a file for storing data
        try{
            FileWriter fw = new FileWriter(this.account_no+"");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("account created"+"\nAccount-holder: "+this.accHolderName+"\nAccount-number: "+this.account_no+
                    "\ninitial " +
                    "balance: "+
                    this.balance+"\nDate: "+ new Date()
            );
            bw.close();
            fw.close();
        Thread.sleep(5000);}
        catch (Exception e){
            System.out.println("some error has occurred in storing data ");
        }

    }


}





//ATMClass start
class ATMClass {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<AccountClass> list = new ArrayList<>();

//withdraw function
    static void withdraw(AccountClass acc) throws IOException, InterruptedException {
        System.out.println("enter the amount to be withdrawn..");
        double with = sc.nextDouble();
        sc.nextLine();
        if(with>acc.balance){
            System.out.println("! insufficient balance ");
            Thread.sleep(3000);
        }
        else{
            acc.balance -= with ;
            BufferedWriter bw = new BufferedWriter(new FileWriter(acc.account_no+"",true));
            bw.write("\n\namount withdrawn..... "+"\nAccount-holder: "+acc.accHolderName+"\nAccount-number: "+ acc.account_no+
                    "\namount withdrawn: "+with+
                    "\ntotal balance: "+ acc.balance+
                    "\nDate: "+ new Date()
            );
            bw.close();
            Thread.sleep(3000);
            System.out.println("transaction performed successfully");
            Thread.sleep(2000);
        }

    }

//transferMoney function
    static void transferMoney(AccountClass acc) throws IOException, InterruptedException {

        System.out.println("enter receiver account number ");
        int acc_no = sc.nextInt();
        sc.nextLine();
        int r_index = checkForAccountByAccountNo(acc_no);

        if(r_index!=-1){
            System.out.println("enter the amount to transfer..");
            double with = sc.nextDouble();
            sc.nextLine();

            if(with>acc.balance){
                System.out.println("! insufficient balance ");
                Thread.sleep(3000);
            }
            else{acc.balance -= with ;
                BufferedWriter bw = new BufferedWriter(new FileWriter(acc.account_no+"",true));
                bw.write("\n\namount transferred..... "+"\nAccount-holder: "+acc.accHolderName+"\nAccount-number: "+acc.account_no+
                        "\naccount transferred to "+list.get(r_index).account_no+
                        "\namount withdrawn: "+ with+
                        "\ntotal balance:  "+acc.balance+
                        "\nDate: "+ new Date()
                );
                bw.close();
                list.get(r_index).balance+=with;
                BufferedWriter bww = new BufferedWriter(new FileWriter(list.get(r_index).account_no+"",true));
                bww.write("\n\namount transferred..... "+"\nAccount-holder: "+list.get(r_index).accHolderName+
                        "\nAccount" +
                        "-number: "+list.get(r_index).account_no+
                        "\naccount transferred from "+acc.account_no+"\namount deposited: "+ with+
                        "\ntotal balance:  "+list.get(r_index).balance+
                        "\nDate: "+ new Date()
                );
                bww.close();
                Thread.sleep(2000);
                System.out.println("transaction occurred successfully");
                Thread.sleep(2000);
            }
        }

        else{
            System.out.println("! receiver does not exist");
        }
    }


//deposit function
    static void deposit(AccountClass acc) throws IOException, InterruptedException {

        System.out.println("enter the amount to deposit..");
        double depo = sc.nextDouble();
        sc.nextLine();
        acc.balance += depo;
        BufferedWriter bw = new BufferedWriter(new FileWriter(acc.account_no+"",true));
        bw.write("\n\namount deposited..... "+"\nAccount-holder: "+acc.accHolderName+"\nAccount-number: "+ acc.account_no+
                "\namount withdrawn: "+depo+
                "\ntotal balance: "+ acc.balance+
                "\nDate: "+ new Date()
        );
        bw.close();
        Thread.sleep(2000);
        System.out.println("transaction occurred successfully");
        Thread.sleep(2000);
    }

//seeDetails function for history of transactions
    static void seeDetails(AccountClass acc) throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new FileReader(acc.account_no+""));
        String str;
        while((str = br.readLine())!=null){
            System.out.println(str);
        }
        br.close();

        Thread.sleep(10000);
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


//balanceInquiry function to know the current balance
    static void balanceInquiry(AccountClass acc) throws InterruptedException {
        System.out.println("total balance : "+acc.balance +" rs");
        Thread.sleep(3000);
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


//actions function for running atm
    static  void actions() throws InterruptedException {

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("WELCOME TO ATM");
        System.out.println("enter your account-Id");
        String account_id = sc.nextLine();

        int index = checkForAccountByAccountID(list,account_id);

        if( index !=-1){
            System.out.println("enter the pin");
            int pin = sc.nextInt();
            sc.nextLine();
            if(list.get(index).pin==pin){
                performAction(index);
            }
            else{
                System.out.println("wrong pin entered");
                Thread.sleep(2000);
            }

        }
        else{
            System.out.println("the account does not exists");
            Thread.sleep(2000);
        }


    }


//performAction function for displaying the menu and calling function like deposit(), withdraw() etc
    private static void performAction(int index)  {
        System.out.println("""
                Select your transaction:
                 WITHDRAWAL -type 1\t\tTRANSACTION HISTORY -type 5
                 DEPOSIT    -type 3\t\tBALANCE INQUIRY:    -type 4
                 TRANSFER   -type 2\t\tEXIT                -type 6"""
        );
        int choice  =  sc.nextInt();
        sc.nextLine();
        try{
            switch (choice) {
                case 1 ->  withdraw(list.get(index));
                case 2 ->  transferMoney(list.get(index));
                case 3 -> deposit(list.get(index));
                case 4 ->  balanceInquiry(list.get(index));
                case 5 -> seeDetails(list.get(index));
                case 6 -> System.out.println("thank-you");}
        }catch(Exception E){
            System.out.println(E.getMessage());
        }
    }



    private static int checkForAccountByAccountID(ArrayList<AccountClass> list, String a_id) {
        for(int i = 0;i<list.size();i++){
            if(list.get(i).account_id.equals(a_id))return i;
        }
        return -1;
    }

    private static int checkForAccountByAccountNo(int acc_no) {
       int index = acc_no-1000-1;
        System.out.println(index);
        if(index<list.size()){
            return index;
        }
       else return-1;
    }

}



//main class

public class RunATM {

    public static void main(String[] args) throws InterruptedException {

        for(int i=0;i<2;i++){
            AccountClass obj = new AccountClass();
            obj.createAccount();
        }


        while(true){
            ATMClass.actions();
        }

    }

}
