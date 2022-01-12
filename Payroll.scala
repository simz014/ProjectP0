import java.util.Scanner
import java.sql.DriverManager
import java.sql.Connection
import java.io._
import javax.crypto.BadPaddingException
import java.io.File
import java.io.PrintWriter
import java.util.Calendar
import scala.util.control._

object Payroll {

    def main(args: Array[String]):Unit={
        
     var scanner=new Scanner(System.in)
       //hourly pay rate
       var  rate= 12.50
       var paymentAmount:Double=0;
       var typeEmployment="Hourly" 
       val writeLog =new PrintWriter(new File("queryLog.log"))

        // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/Payroll" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "4370335s" // Update to include your password

    var connection:Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
   
      // create the statement, and run the select query
     val statement = connection.createStatement()
     val statement2 = connection.createStatement()
   
     /*This is ran once to create the Employee table for Payroll Database **/
     /**
     val creat_Table = statement.executeUpdate("Create table EmployeePay(PaycheckID serial, EmployeeID Int, firstName varchar(45),lastName varchar(45), Employment_Type varchar(255),"+ 
                                            " hoursWorked Decimal (10,2), Hourly_Rate Decimal(10,2),Date_Time datetime default current_timestamp on Update current_timestamp,"+
                                            " PRIMARY KEY (PaycheckID), FOREIGN KEY(EmployeeID) REFERENCES `payroll`.`company`(EmployeeID));")
     writeLog.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'Create table EmployeePay(PaycheckID serial, EmployeeID Int, firstName varchar(45),lastName varchar(45), Employment_Type varchar(255),"+ 
                                            " hoursWorked Decimal (10,2), Hourly_Rate Decimal(10,2),Date_Time datetime default current_timestamp on Update current_timestamp,"+
                                            " PRIMARY KEY (PaycheckID), FOREIGN KEY(EmployeeID) REFERENCES `payroll`.`company`(EmployeeID);'\n")
 
                                            */
      /*This is ran once to create the view in database  
    val creat_view = statement.executeUpdate("create view  Paycheck AS select PaycheckID, concat(firstName ,' ', lastName) as FullName, Employment_Type,"+
                                               " Hourly_Rate,hoursWorked,(hoursWorked-40) as Overtime, (Hourly_Rate*hoursWorked ) as TotalPay from Employeepay where PaycheckID =(SELECT max(PaycheckID) FROM Employeepay);") 
                                           */                                                          
                                  
       println("================================================================================")                           
       println("              "+   "Employee Payroll Calculator "+  "    ")
       println("================================================================================")   
        
        var arrOfIds= Array(100,101,103,104,105,106,107,108,109,110)
       println(" Please enter your EmployeeID to Begin")
         var empID=scanner.nextInt()
         var counter=3
       while( (arrOfIds contains (empID))!=true){
       println("EmployeeID is not in company Database, try again!")
       empID=scanner.nextInt()
       if(counter>2) 
           println("You made too many attempts goodbye") 
           counter=counter+1
          empID=scanner.nextInt()
       }
             scanner.nextLine() 
       println("Please eneter your First Name(A-Z)")
          var firstName=scanner.nextLine() 
        
           println("Please eneter your Last Name(A-Z)")
          var  lastName=scanner.nextLine()
            
            println("Please eneter your total Weekly hours")   
            var  hoursWorked= scanner.nextInt()
            
          if(hoursWorked<=40) {
           paymentAmount=hoursWorked*rate
              
          }
       
          while (hoursWorked>=60){
            println("Company does not allow working more than 60hrs be week")
            println("Please eneter your total Weekly hours") 
            hoursWorked= scanner.nextInt()
            if((hoursWorked < 60) && (hoursWorked > 0) )
             hoursWorked;
          }
          
          
          /** Overtime calculation **/
          paymentAmount=(40*rate + (hoursWorked-40)*rate*1.5)
          
          
         println(" Is this info correct? enter Y/N")
         println("FullName :" +firstName+" "+lastName+ " :Hours Worked:" +hoursWorked)
         println("========================================================================================================")
          var verification=scanner.next().toString.toUpperCase()
          val query="INSERT INTO employeepay(firstName, lastName,Employment_Type, hoursWorked,Hourly_Rate) Values ('"+firstName+"','" +lastName+"','"+typeEmployment+"'," +
                                                     ""+hoursWorked+","+rate+");" 
   
       // println(query)
        val resultSet1=statement.executeUpdate(query)
        writeLog.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO employeepay(firstName, lastName,Employment_Type, hoursWorked,Hourly_Rate) Values ('"+firstName+"','" +lastName+"','"+typeEmployment+"'," +
                                                     ""+hoursWorked+","+rate+");'\n")
         /** code cause user if input is accurrate if No it deletes from DB **/
          while (verification =="N"){
            val resultSet2=statement.executeUpdate("delete from employeepay order by EmployeeID desc limit 1;")
            writeLog.write(Calendar.getInstance().getTimeInMillis +" -Executing 'delete from employeepay order by EmployeeID desc limit 1;'\n")
               println("Please try again")
               println("Please eneter your First Name(A-Z)")
               var firstName=scanner.next() 
             println("Please eneter your Last Name(A-Z)")
            var  lastName=scanner.next()
            
            println("Please eneter your total Weekly hours")   
            var  hoursWorked= scanner.nextInt()   
            println(" Is this info correct? enter Y/N")
          println("FullName :" +firstName+" "+lastName+ " :Hours Worked:" +hoursWorked)
           println("========================================================================================================")
          val query="INSERT INTO employeepay(firstName, lastName,Employment_Type, hoursWorked,Hourly_Rate) Values ('"+firstName+"','" +lastName+"','"+typeEmployment+"'," +
                                                     ""+hoursWorked+","+rate+");" 
         
            val resultSet1=statement.executeUpdate(query)
           writeLog.write(Calendar.getInstance().getTimeInMillis +" -Executing 'INSERT INTO employee(firstName, lastName,Employment_Type, hoursWorked,Hourly_Rate) Values ('"+firstName+"','" +lastName+"','"+typeEmployment+"'," +
                                                     ""+hoursWorked+","+rate+");'\n")
                        writeLog.close()

               verification=scanner.next().toString.toUpperCase() 

        }
         /** Printing the headings**/
       println("=============================================================================================================================================")                    
       println("PayCheckID "+ "\t" +"FullName"+ "\t "  +"EmployeeType "+ "\t\t"  +"Hourly_Rate "+ "\t\t"  +"HoursWorked "+ "\t\t" +"Overtime "+ "\t\t"   +"TotalPay ")
       println("=============================================================================================================================================")
        //val resultDb = statement.executeQuery("SELECT * FROM employee;")
        val resultDbView = statement.executeQuery("SELECT * FROM Paycheck;")
          writeLog.write(Calendar.getInstance().getTimeInMillis +" -Executing 'SELECT * FROM Paycheck;'\n")                                                                         
      while ( resultDbView.next())
        
        println(resultDbView.getString(1) + "\t\t " + resultDbView.getString(2) + " \t\t  " + resultDbView.getString(3)+ " \t\t  "+resultDbView.getString(4)
        + " \t\t  "+resultDbView.getString(5)+ " \t\t  "+resultDbView.getString(6)+ " \t\t\t "+resultDbView.getString(7)) 
        println()
      }
    
    catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
    writeLog.close()

}
 
}