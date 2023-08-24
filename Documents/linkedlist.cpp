#include<iostream>
using namespace std;

class node{
	public:
	int d;
	node* next;
};
node* head=NULL;
void insertatfirst(int d){
	node *n=new node();
	n->d=d;
	n->next=head;
	head=n;
}

void find(int d){
	node *temp=head;
	int i=1;
	while(temp->next!=NULL){
		if(temp->d==d){
			cout<<"\nfound position: "<<i;
			return;
		}
		i++;
		temp=temp->next;
	}
	cout <<"\n not found\n";
	
}
void insertatlast(int d){
	node *new_node=new node();
	new_node->next=NULL;
	new_node->d=d;
	node *temp=head;
	if (head==NULL){
		head=new_node;
		return;
	}
	else if(head->next==NULL){
		head->next=new_node;
		return;
	}
	else if (head->next!=NULL){
		
		while(temp->next!=NULL){
			temp=temp->next;
		}
		temp->next=new_node;
	}
}
void delete_node (int d){
	node *temp=head;
	int i=1;
	if(temp->d==d){
		head=temp->next;
		return;
	}
	while(temp->next!=NULL){
		if(temp->next->d==d){
			temp->next=temp->next->next;
			cout<<"\ndeleted successfully: "<<i;
			return;
		}
		i++;
		temp=temp->next;
	}
	cout<<"\n not found"<<endl;
}
void display(){
	node *temp=head;
	while(temp!=NULL){
		cout<<temp->d<<",";
		temp=temp->next;
	}
}
int main(){
	insertatlast(1);
	insertatlast(2);
	insertatlast(3);
	insertatlast(4);
	display();
	find(3);
	delete_node(1);
	display();
}
