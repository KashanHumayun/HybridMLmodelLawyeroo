#include<iostream>
using namespace std;
bool isPrime(int n){
	for(int i=2;i<n;i++){
		if(n%i==0){
			return false;
		}
	}
	return true;
}
int main(){
	int n;
	cout<<"Please Enter Any Number Above 5?/n";
	cin>>n;
	if(n>=5){
		for(int i=2;i<=n;i++){
			if(isPrime(i)==true){
				for(int j=2;j<=n;j++){
					if(isPrime(j)==true){
						for(int k=2;k<=n;k++){
							if(isPrime(k)==true){
								if(i+j+k==n) cout<<i<<"+"<<j<<"+ "<<k<<" = "<<n<<"\n";		
							}
						}
					}
				}
			}
		}
	}
	else cout<<"Sorry You Entered the Wrong Input!";	
}
