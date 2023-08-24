#include<iostream>
using namespace std;
class laptop{
	private:
		string brand;
		string model;
		int serial;
		string color;
		float price;
		float processor_speed;
		int ram;
		float screen_size;
	public:
		laptop(string brand,string model,int serial,string color,float price,
		float processor_speed,int ram, float screen_size){
			this->brand=brand;
			this->model=model;
			this->serial=serial;
			this->color=color;
			this->price=price;
			this->processor_speed=processor_speed;
			this->ram=ram;
			this->screen_size=screen_size;
		}	
		void Display(){
			cout<<"Brand : "<<brand;
			cout<<"\nModel : "<<model;
			cout<<"\nSerial : "<<serial;
			cout<<"\nColor : "<<color;
			cout<<"\nPrice : "<<price;
			cout<<"\nProcessor Speed : "<<processor_speed;
			cout<<"\nRam : "<<ram;
			cout<<"\nScreen Size : "<<screen_size;
		}
};
int main(){
	laptop lap("Hp","Pavillion",23453,"Blue",345.55,3.2,16,15);
	laptop lap1("Lenovo","Yoga",23753,"Yellow",555.55,3.4,16,15);
	laptop lap2("Dell","XPS",21466,"Black",665.55,3.1,8,15);
	
	lap.Display();
	cout<<"\n\n\n";
	
	lap1.Display();
	cout<<"\n\n\n";
	
	lap2.Display();
	cout<<"\n\n\n";
}
