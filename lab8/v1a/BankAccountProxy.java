public class BankAccountProxy implements BankAccount{
	private BankAccount bank;
	public BankAccountProxy(BankAccount bank) {
		this.bank=bank;
	}

	@Override
	public void deposit(double amount) {
		this.bank.deposit(amount);
	}

	@Override
	public boolean withdraw(double amount) {
		if(Company.user==User.COMPANY) {
			return false;
		}
		return this.bank.withdraw(amount);
	}

	@Override
	public double balance() {
		if(Company.user==User.COMPANY) {
			return Double.NaN;
		}else {
			return this.bank.balance();
		}
	}

}