# websearch-gui
Searches for and displays results from craigslist.org for valid vehicle listings matching user criteria.

Description
-----------
Starts the web-searching program. The program first prompts the user with an interactive gui for a vehicle model
to search craigslist.org for, and then it parses the resulting advertisements (using the external library JSoup),
filtering out irrelevant listings based on certain criteria, including posting location and posting date
(i.e. filters out old listings that are from nearby cities). The "resources" folder above contains images
showing the interface of the program.
- Requires: jsoup-1.11.3+

Project Breakdown
-----------------
- Follows Model, View, Controller (MVC) design pattern
- **Main**: contains the main method; starts the program
- **Search**: represents one search with the parameters of the query 
  - (i.e. price, miles, year, etc.)
- **Advertisement**: represents one listing on the website 
  - (title, location, url, attributes, age, images, description)
- **Util**: static utilities methods to use across all classes
### Model
- **IModelSearch**: interface represeting the model to execture searching and parsing of the website
- **AbstractModelSearch**: abstract implementation for both single- and multi-threaded implementation
- **ModelSearchST**: class implementation of the single-threaded model
- **ModelSearchMT**: class implementation of the multi-threaded model
- **ModelSearchMTWorker**: single thread worker for the multi-threaded model
### View
- **IViewSearch**: interface representing the views displayed to the user
  - includes both model selection and presentation of results
- **ViewSearch**: class implementation of the view
- **AbstractPanel**: abstracted class for the gui panels displayed to the user
- **PanelModelSelector**: concrete class that extends AbstractPanel to display the model selection screen to the user
- **PanelViewResults**: concrete class that extends AbstractPanel to display the search results screen to the user
- **PanelAttributeEdit**: concrete class that extends AbstractPanel to display the search option editor to the user
### Controller
- **IControllerSearch**: interface representing the controller to handle user input and program execution 
  - (handles executing the search and displaying the results)
- **ControllerSearch**: class implementation of the controller 
  - (acts as **ActionListener**, **KeyListener**, and **ListSelectionListener**)
