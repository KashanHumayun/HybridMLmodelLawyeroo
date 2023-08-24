#include<iostream>
using namespace std;
int main(){
	string s;
	bool f=false;
	cout<<"Enter String!";
	cin>>s;
	int a[s.length()]={0};
	for(int i=0;i<s.length();i++){
		for(int j=0;j<s.length();j++){
			if(i!=j)
				if(s[i]==s[j])
					a[i]=a[i]+1;
		}
	}
	for(int g=0;g<s.length();g++){
		if(a[g]!=1)
			{f=false;
			break;}
		else f=true;
	}
	if(f==true) cout<<s<<" is pair isogram.";
	else cout<<s<<" is not a pair isogram";
}
