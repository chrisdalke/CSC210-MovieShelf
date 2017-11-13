var script = document.createElement('script');
script.src = 'http://code.jquery.com/jquery-1.11.0.min.js';
script.type = 'text/javascript';
var movieIndex=0;


//***Testing Data***
var list=["Inception","Interstellar","Kung-Foo Panda","The Room","Scooby-Doo","Titanic","Monsters Inc.","The Prestige","Sinister"];
var movieDescription="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices.";

var fullListing=[["Inception","woof",2010,"Action","description"],
                 ["Interstellar","image",2015,"genre","description"],
                 ["Kung-Foo Panda","image",2010,"genre","description"],
                 ["Scooby-Doo","image",2000,"genre","description"],
                 ["Monsters Inc.","image",2009,"genre","description"],
                 ["The Prestige","image",2015,"genre","description"],
                 ["Sinister","image",2015,"genre","description"],
                ];
//******end*******

/*
var movieListing=new Map();

var Movie= class{
    constructor(title,thumbnail,year,genre,description){
        this.title=title;
        this.thumbnail=thumbnail;
        this.year=year;
        this.genre=genre;
        this.description=description;
    }
}

function mapMovie(entry,lastIndex){
    movieListing.put(lastIndex+1,
                     new Movie(entry[0],entry[1],entry[2],entry[3],entry[4]));
}
*/

$(document).ready(function(){
    initializeShelf(list);
    $(".card").click(function(){
        var index='#d'+($(this).attr("data-index"));
        var p = $(index);
        if(p.is(':visible')){
            p.slideToggle();
        }else{
            $(".preview").hide();
            $(index).slideToggle();
        }
       
    })
});

/*
 * Initialize the list of movies on the shelf
 */
function initializeShelf(){
    //var list=new Array(0);
    var numMovies=list.length;
    for(i=0;i<numMovies;i++){
       var title=list[i];
       var currentGrid=$(".grid:last");
       var value = parseInt(currentGrid.attr("data-value"));
       if(value<4){
           addToGrid(currentGrid,title);
           currentGrid.attr("data-value",String(value+1));
       } else{
           var masterGrid=$("#gridElement");
           addNewGrid(masterGrid,list[i]);
       }
    movieIndex++;
    }
    if(numMovies==0){
        var currentGrid=$(".grid:last");
        var outerDiv=$("<div></div>");
        var card=$("<div>Search For Movies Here!</div>");
        card.addClass("uk-card uk-card-secondary uk-height-large uk-card-body uk-card-hover card");
        outerDiv.append(card);
        currentGrid.append(outerDiv);
    }
}

/*
 * Handle adding individual movies to the shelf
 */

function addToGrid(grid,movie){
    var outerDiv=$("<div></div>");
    var card=$("<div>"+movie+"</div>");
    card.addClass("uk-card uk-card-secondary uk-height-large uk-card-body uk-card-hover card");
    card.attr("data-index",movieIndex);
    outerDiv.append(card);
    grid.append(outerDiv);
    insertPreview(grid,movieDescription);
}

/*
 * Handle expanding shelf to accomodate new rows
 */
function addNewGrid(master,movie){
    var newGrid=$("<div uk-grid></div>");
    newGrid.attr("data-value","1");
    newGrid.addClass("uk-grid-match uk-child-width-1-4 uk-grid-small uk-text-center uk-padding-large grid");
    addToGrid(newGrid,movie,movieIndex);
    master.append(newGrid);
    insertPreview(newGrid,movieDescription);
}

function insertPreview(grid,element){
    var outerDiv=$("<div></div>");
    var text =$("<p>"+movieDescription+"</p>");
    outerDiv.addClass("uk-section uk-container-large uk-section-secondary preview");
    outerDiv.attr("id",'d'+String(movieIndex))
    outerDiv.append(text);
    outerDiv.insertAfter(grid);
}