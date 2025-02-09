package lazyTrees;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Simulates the market scenario of buying and adding items to a store's inventory.
 * Implements BST with lazy deletion to keep track of total inventory ("deleted" + non deleted) 
 * and current inventory (non deleted only).
 *
 * @author Foothill College, [YOUR NAME HERE]
 */
public class SuperMarket 
{
	public static final boolean SHOW_DETAILS = true;

	PrintObject<Item> printObject = new PrintObject<Item>();

	// The data structure, which we use to add and remove items.
	private LazySearchTree<Item> inventory;

	/**
	 * Instantiates inventory to be a LazySearchTree of Item objects.
	 */
	public SuperMarket()
	{
		inventory = new LazySearchTree<Item>();
	}

	/**
	 * Add a new item with the name as in parameter into inventory. If there is 
	 * already same name product, increase amount by one, if not create a new object.
	 * @param item		The item to be added to the inventory tree.
	 */
	public void addToInventory(String item)
	{
		// Create a temporary object to hold the item.
		Item tmp = new Item(item);

		// Check if the item is in the inventory tree.
		boolean isFound = inventory.contains(tmp);

		// If the item is not found, add the temporary object as another node (category) to the tree.
		if (!isFound)
		{
			inventory.insert(tmp);

			// NOTE: Need to check if the item was lazily deleted, then we need to increment the count
			Item found = inventory.find(tmp);
			if (found.getCount() == 0)
			{
				found.incrementCount();
			}
			return;
		}

		// If the item is found, increase the number of items in that item category.
		Item found = inventory.find(tmp);

		// item was previously in tree, so increment the count
		found.incrementCount();
	}

	/**
	 * If the item is in the inventory, decrease the count by one. 
	 * If only one item is left, remove it from the inventory. 
	 * @param item		The item to be removed to the inventory tree.
	 */
	public void removeFromInventory(String item)
	{
		Item tmp = new Item(item);
		boolean isFound = inventory.contains(tmp);

		// check if the item exists in the inventory disregarding lazy deletion
		if (!isFound)
		{
			throw new NoSuchElementException();
		}

		Item found = inventory.find(tmp);

		// if the items has zero left in stock, 
		// then treat it as if it does not exist in the tree.
		if (found.getCount() == 0)
		{
			throw new NoSuchElementException();
		}
		// if the item has one left in stock, 
		// then decrement its count and lazy delete it in the tree. 
		else if (found.getCount() == 1)
		{
			found.decrementCount();

			inventory.remove(tmp);	
		}
		// otherwise, simply decrement its count.
		else
		{
			found.decrementCount();
		}
	}


	/** 
	 * Display the first item and last time of the soft tree in lexical order.
	 */
	public void showFirstAndLastItem(String message)
	{
		System.out.println("\n" + message);

		try
		{
			Item min = inventory.findMin();
			System.out.println ( "First item: " + min.toString());
		} 
		catch (Exception NoSuchElementException)
		{
			System.out.println("Warning: minimum element not found!");
		}

		try
		{
			Item max = inventory.findMax();
			System.out.println ( "Last item: " + max.toString());
		} 
		catch (Exception NoSuchElementException)
		{
			System.out.println("Warning: minimum element not found!");
		}

	}

	/**
	 * Shows the state of the tree by displaying:
	 * - the *hard* inventory, which includes deleted nodes.
	 * - the *soft* inventory, which excludes deleted nodes.
	 * @param message	Additional details about the state.
	 * @param showTree	Set to true if we want to display the contents of the tree
	 */
	protected void displayInventoryState(String message, boolean showTree)
	{
		System.out.println("\n" + message);
		System.out.println("\"hard\" number of unique items (i.e. mSizeHard) = " + inventory.sizeHard());
		System.out.println("\"soft\" number of unique items (i.e. mSize) = " + inventory.size());

		if (!showTree)
			return;

		System.out.println( "\nTesting traversing \"hard\" inventory:");

		inventory.traverseHard(printObject);


		System.out.println( "\n\nTesting traversing \"soft\" inventory:");

		inventory.traverseSoft(printObject);
		System.out.println("\n");
	}

	public static void main(String[] args) 
	{
		//final String TESTFILE = "resources/inventory_log.txt";
		//final String TESTFILE = "resources/inventory_log2.txt";

		// NOTE: Short inventory file to test for removal of root node from LazySearchTree
		//final String TESTFILE = "resources/inventory_short.txt";

		// NOTE: An example of testing the boundary condition when removing an item that may not exist
		//final String TESTFILE = "resources/inventory_invalid_removal.txt";

		final String TESTFILE = "resources/inventory_veryshort.txt";

		System.out.printf("Test file: %s \n", TESTFILE);

		SuperMarket market = new SuperMarket();

		File infile = new File(TESTFILE);

		try 
		{
			Scanner input = new Scanner(infile);

			String line = "";
			int lineNum = 0;
			while (input.hasNextLine()) 
			{
				lineNum++;
				line = input.nextLine(); 
				String [] tokens = line.split(" ");

				String selection = tokens[0];
				String itemName = tokens[1];

				String message = "at line #" + lineNum + ": " + line;

				// When an item is added:
				// If the item is not in our inventory, 
				// create a new entry in our inventory.
				// Otherwise, increment the count of the item.
				if (selection.equals("add"))
				{
					market.addToInventory(itemName);

					// NOTE: Currently displaying the contents is disabled to reduce cluttering the output.
					// Suggestion: To start, enable displaying the contents of the tree to help you debug.
					if (SHOW_DETAILS)
						market.displayInventoryState("\nUpdate " + message, true);
				}

				// When an item is bought: 
				// Decrement the count of the item.
				// If the item is out of stock, 
				// remove the item from inventory.
				//
				// Note: buying an out of stock item, is invalid. Handle it appropriately.
				else if (selection.equals("buy"))
				{
					try
					{
						market.removeFromInventory(itemName);

						// NOTE: Currently displaying the contents is disabled to reduce cluttering the output.
						// Suggestion: To start, enable displaying the contents of the tree to help you debug.
						if (SHOW_DETAILS)
							market.displayInventoryState("\nUpdate " + message, true);						
					}
					catch (java.util.NoSuchElementException ex)
					{
						// NOTE: Ideally we'd print to the error stream,
						//       but to allow correct interleaving of the output
						//       we'll use the regular output stream.
						System.out.printf("\nWarning: Unable to fulfill request: %s \n", message);
						System.out.printf("Warning: Item %s is out of stock.\n", itemName);
					}
				}
				else
				{
					System.out.println("Warning: Inventory selection not recognized!");
				}		

				// Display the first item and the last item before checking
				// if it's time to clean up our inventory.
				if (SHOW_DETAILS)
					market.showFirstAndLastItem(message);
			}
			input.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 

		// Display the inventory
		System.out.println("\n");
		market.displayInventoryState("\nFinal state of inventory:", true);

		// flush the error stream
		System.err.flush();

		System.out.println("\nDone with SuperMarket.");
	}
}
