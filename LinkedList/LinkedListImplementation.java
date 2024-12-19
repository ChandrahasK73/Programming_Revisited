class Node1
{
	int data;
	Node1 next;
}

class LinkedList1
{
	Node1 head;
	public void insert(int data)
	{
		Node1 n=new Node1();
		n.data=data;
		n.next=null;
		if(head==null)
		{
			head=n;
		}
		else
		{
			Node1 n1=head;
			while(n1.next!=null)
			{
				n1=n1.next;
			}
			n1.next=n;
		}
	}
	
	public void insertAtStart(int data)
	{
		Node1 n=new Node1();
		n.data=data;
		n.next=head;
		head=n;
	}

	public void insertAt(int index,int data)
	{
		Node1 n=new Node1();
		n.data=data;
		n.next=null;
		if(index==0)
		{
			insertAtStart(data);
		}
		else
		{
			Node1 n1=head;
			for(int i=0;i<index-1;i++)
			{
				n1=n1.next;
			}
			n.next=n1.next;
			n1.next=n;
		}
	}

    public int findMiddle() {
        if (head == null) return -1;

        Node1 slow = head;
        Node1 fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow.data;
    }

    public int search(int data) {
        Node1 current = head;
        int index = 0;

        while (current != null) {
            if (current.data == data) {
                return index;
            }
            current = current.next;
            index++;
        }

        return -1;  // Data not found
    }

    public int count() {
        int count = 0;
        Node1 current = head;
        
        while (current != null) {
            count++;
            current = current.next;
        }
        
        return count;
    }

    public boolean hasCycle() {
        Node1 slow = head;
        Node1 fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }
        
        return false;
    }

    public static LinkedList1 mergeSorted(LinkedList1 list1, LinkedList1 list2) {
        LinkedList1 mergedList = new LinkedList1();
        Node1 p1 = list1.head;
        Node1 p2 = list2.head;

        while (p1 != null && p2 != null) {
            if (p1.data < p2.data) {
                mergedList.insert(p1.data);
                p1 = p1.next;
            } else {
                mergedList.insert(p2.data);
                p2 = p2.next;
            }
        }

        while (p1 != null) {
            mergedList.insert(p1.data);
            p1 = p1.next;
        }

        while (p2 != null) {
            mergedList.insert(p2.data);
            p2 = p2.next;
        }

        return mergedList;
    }

    public void removeDuplicates() {
        Node1 current = head;

        while (current != null && current.next != null) {
            if (current.data == current.next.data) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
    }

	public void deleteAt(int index)
	{
		if(index==0)
		{
			head=head.next;
		}
		else
		{
			Node1 n=head;
			Node1 n1=null;
			for(int i=0;i<index-1;i++)
			{
				n=n.next;
			}
			n1=n.next;
			n.next=n1.next;
			n1=null;
		}
	}
	public void reverse()
	{
		Node1 current=head;
		Node1 prev=null;
		Node1 next=null;
		while(current!= null)
		{
			next=current.next;
			current.next=prev;
			prev=current;
			current=next;
		}
		head=prev;
	}
	public void show()
	{
		Node1 n=head;
		while(n.next!=null)
		{
			System.out.print(n.data+"->");
			n=n.next;
		}
			System.out.println(n.data);
	}
}


class MainMethod
{
	public static void main(String args[])
	{
		LinkedList1 list = new LinkedList1();
        list.insert(10);
        list.insert(20);
        list.insert(20);
        list.insert(30);
        list.insert(40);

        list.show();
        System.out.println("Middle element: " + list.findMiddle());
        System.out.println("Index of 20: " + list.search(20));
        System.out.println("Total count: " + list.count());

        LinkedList1 list2 = new LinkedList1();
        list2.insert(5);
        list2.insert(15);
        list2.insert(25);

        LinkedList1 mergedList = LinkedList1.mergeSorted(list, list2);
        mergedList.show();

        System.out.println("Removing duplicates from the original list:");
        list.removeDuplicates();
        list.show();

        System.out.println("Checking for cycle: " + list.hasCycle());
	}
}