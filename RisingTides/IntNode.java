/**
 * Problem Set 2
 * Writing methods for an IntNode class
 * 
 * @author Steven John
 * @date 12/20/24
 * @period PD:1
 */
public class IntNode {
	  public int data;
      public IntNode next;
      public IntNode() {
    	  this.data = 0; 
    	  this.next = null;
      }
      public IntNode(int data, IntNode next) {
          this.data = data; 
          this.next = next;
      }
      public String toString() 
      {
    	  if(next != null)
    		  return data + " " + next.toString();
          return data + "";
      }

      /** #1
       * Implement the addBefore method such that newItem will be added before 
       * target in the list starting with front.  If the target does not exist,
       * addBefore should return the original list, unchanged.
       * 
       * @param front, the first node in the linked list
       * @param target 
       * @param newItem item to be inserted
       * @return the front node of the updated linked list
       */
      public static IntNode addBefore(IntNode front, int target, int newItem) {
    	  if (front == null) return front;
    	  if (front.data == target) {
    	      front = new IntNode(newItem, front);
    	  } else {
    	      IntNode current = front;
    	      while (current.next != null && current.next.data != target) {
    	          current = current.next;
    	      }
    	      if (current.next != null && current.next.data == target) {
    	          current.next = new IntNode(newItem, current.next);
    	      }
    	  }
    	  return front;
      }
      /** #2
       * Implement the addBeforeLast method such that newItem will be added 
       * just before the last item in the linked list.  If the initial list is 
       * empty addBeforeLast should return null, returning the original list, unchanged.
       * 
       * @param front, the first node in the linked list
       * @param newItem
       * @return the front node of the updated linked list
       */
      public static IntNode addBeforeLast(IntNode front, int newItem) {
    	  if (front == null) return front;
    	    if (front.next == null) front = new IntNode(newItem, front);
    	    else {
    	        IntNode current = front;
    	        while (current.next.cb next != null) current = current.next;
    	        current.next = new IntNode(newItem, current.next);
    	    }
    	    return front;
    	}
      
      /** #3
       * Implement the method numberOfOccurrances that will search 
       * a given linked list for a target int, and return the 
       * number of occurrences of the target
       * 
       * @param front, the first node in the linked list
       * @param target
       * @return the number of occurrences of the target
       */
      public static int numberOfOccurrences(IntNode front, int target) {
    	  int count = 0;
    	  IntNode current = front;
    	  while(current != null) {
    		  if(current.data == target) {
    			  count++;
    		  }
    		  current = current.next;
    	  }
    	  return count; 
      }
      
      /** #4
       * Implement the method deleteEveryOther to delete EVERY OTHER 
       * item from an integer linked list. 
       * For example:
       * 	before: 3->9->12->15->21
       * 	after: 3->12->21
       * 
       * 	before: 3->9->12->15
       * 	after: 3->12
       *
       * 	before: 3->9
       * 	after: 3
       * 	
       * 	before: 3
       * 	after: 3
       * 
       * If the list is empty, the method should do nothing.
       * @param front, the first node in the linked list
       */
      public static void deleteEveryOther(IntNode front) {
    	  if(front != null) {
        	  IntNode current = front;
        	  while(current != null && current.next != null) {
        		  current.next = current.next.next;
        		  current = current.next;
        	  }
          }
      }
      
      /** #5
       * Implement the method deleteAllOccurrences that will 
       * delete all occurrences of a given target int from a 
       * linked list, and return a pointer to the first node 
       * of the resulting linked list.
       * 
       * @param front, the first node in the linked list
       * @param target
       * @return the front node of the updated linked list
       */
      public static IntNode deleteAllOccurrences(IntNode front, int target) {
    	  if (front != null) {
    		    IntNode current = front;
    		    IntNode prev = new IntNode(0, front);
    		    while (current != null) {
    		        if (front.data == target) {
    		            front = front.next;
    		            prev = prev.next;
    		        } else if (current.data == target) {
    		            prev.next = prev.next.next;
    		        } else {
    		            prev = prev.next;
    		        }
    		        current = current.next;
    		    }
    		}
    		return front;
      }

      
      /** #6
       * Implement the method commonElements to find the common elements 
       * in two SORTED linked lists, and return the common elements in 
       * sorted order in a NEW linked list. The original linked lists 
       * should not be modified. 
       * For instance:
       *  	l1 = 3->9->12->15->21
       *  	l2 = 2->3->6->12->19
       *  should produce a new linked list:
       *  	3->12
       *  
       * You may assume that the original lists do not have any duplicate items.
       * Return null if no common elements exist.
       * 
       * @param frontL1, the first node in the linked list 1
       * @param frontL2, the first node in the linked list 2
       * @return A reference to the front node of a new linked list
       * 	which holds the common elements of L1 and L2 in sorted order.
       * 	Or null if no common elements exist.
       */
      public static IntNode commonElements(IntNode frontL1, IntNode frontL2) {
    	  IntNode head = new IntNode();
    	  IntNode pointer1 = frontL1;
    	  IntNode pointer2 = frontL2;
    	  IntNode current = head;
    	  if (pointer1 == null || pointer2 == null) {
    	      return head;
    	  }
    	  while (pointer1 != null) {
    	      IntNode temp = pointer2;
    	      while (temp != null) {
    	          if (pointer1.data == temp.data) {
    	              if (current == head) {
    	                  head = new IntNode(pointer1.data, null);
    	                  current = head;
    	              } else {
    	                  current.next = new IntNode(pointer1.data, null);
    	                  current = current.next;
    	              }
    	          }
    	          temp = temp.next;
    	      }
    	      pointer1 = pointer1.next;
    	  }

    	  return head;
      }
}