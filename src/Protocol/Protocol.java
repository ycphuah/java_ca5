/**
 * This is the Model that maintains the STATE of the interaction.
 * The PROTOCOL is implemented using logic and state transitions.
 *
 *
 * */
package Protocol;

import DAO.MovieDaoInterface;
import DAO.MySQLMovieDao;
import DTO.Movie;
import Exceptions.DaoException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Protocol {

    MovieDaoInterface movieDao = new MySQLMovieDao();


    public String processInput(String theInput) throws DaoException, JSONException{
        String theOutput = null;


            if (theInput.equalsIgnoreCase("Display all movies")) {
                List<Movie> allMovies = movieDao.findAllMovies();
               // movieDao.displayListFormat(allMovies);
               //convert to json
               
               
                theOutput = createJSON(allMovies);
             

            } else if (theInput.substring(0, theInput.indexOf(" ")).equalsIgnoreCase("Search")) {
    
                String input = theInput.substring(theInput.indexOf(" "));
                List<Movie> searchedMovies = movieDao.findMovieByTitle(input.trim());

                movieDao.displayListFormat(searchedMovies);
                theOutput = searchedMovies.toString();
                if ("[]".equals(theOutput)) {
                    List<Movie> searchedDirector = movieDao.findMovieByDirector(input.trim());
                    movieDao.displayListFormat(searchedDirector);
                    theOutput = searchedDirector.toString();
                }
               

            } else if (theInput.substring(0, theInput.indexOf(" ")).equalsIgnoreCase("Add")) {
                //exp: ADD TITLE movietitle GENRE moviegenre DIRECTOR moviedirector
                String[] components = theInput.split(" ");
                movieDao.addMovie(components[2], components[4], components[6]);
                theOutput = "Movie added to the database!";
            

            } else if (theInput.substring(0, theInput.indexOf(" ")).equalsIgnoreCase("Delete")) {
                String[] components = theInput.split(" ");
                movieDao.deleteMovie(components[1]);
                theOutput = "Movie deleted from the database!";
              
            } 
            else if (theInput.substring(0, theInput.indexOf(" ")).equalsIgnoreCase("Update")) {
                //exp: UPDATE oldmovietitle TO newmovietitle
                      String[] components = theInput.split(" ");
                      movieDao.updateMovieByTitle(components[1], components[3]);
                      theOutput = "Movie updated!";
                    ;
            }
            else {
                theOutput = "You're supposed to say \"Who's there?\"! "
                        + "\nTry again. Knock! Knock!";
            }
            
        
        return theOutput;
    }
    
    
    public String createJSON(List<Movie> movieList) throws JSONException {
       
        JSONObject jsonObject = new JSONObject().put("movies", new JSONArray(movieList));
  
        System.out.println("Server - Protocol - createJSON():" + jsonObject.toString());
        
        return jsonObject.toString();
    }
//    
//        public String createJson(List<Movie> movies) throws JSONException {
//        JSONObject json = new JSONObject();
//
//        for (Movie m : movies) {
//            JSONObject jsonMovie = new JSONObject();
//            jsonMovie.put("id", m.getId());
//            jsonMovie.put("title", m.getTitle());
//            jsonMovie.put("genre", m.getGenre());
//            jsonMovie.put("director", m.getDirector());
//
//            //userArray.put(jsonUser);
//            json.append("movies", jsonMovie);
//        }
//           
//        String output = json.toString().replace("},{", "},\n{");
//        
//        return output;


    }
