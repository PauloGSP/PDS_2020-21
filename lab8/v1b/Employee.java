public class Employee{
	private Person p;
	private double salary;
	private BankAccount bankAccount;

	public Employee(Person p, double salary) {
		this.p = p;
		this.salary = salary;
		bankAccount = new BankAccountProxy(new BankAccountImpl("Conta1", 0));
	}

	public double getSalary() {
		return salary;
	}
	
	public BankAccount getBankAccount() {
		return new BankAccountProxy(bankAccount);
	}

}