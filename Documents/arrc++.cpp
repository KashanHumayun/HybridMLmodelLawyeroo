#include<iostream>
using namespace std;
class average_ARR{
		
	public:
		float* arr;
		void setArr(float arr1[]){
			arr=arr1;
		}
		
		void Input(){
			for(int i=0;i<10;i++){
				cout<<"Enter Number: "<<endl;
				cin>>arr[i];
			}
		}
		float sum(){
			float summ=0.0;
			for(int i=0;i<10;i++){
				summ=summ+arr[i];
			}
			return summ;
		}
		double aver(){
			double average=sum()/10;
			return average;
		}
		void output(){
			for(int i=0;i<10;i++){
				cout<<arr[i]<<"\t\t";
			}
		}
};
int main(){
	average_ARR averr;
	float array[10];
	averr.setArr(array);
	averr.Input();
	cout<<"sum: "<<averr.sum();
	cout<<"\n\nOutput: ";averr.output();
	cout<<"\n\nAverage: "<<averr.aver();
}
