package rest.services;

import model.Artist;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ICrudRepository;
import repository.RepositoryException;

import java.util.Collection;


/**
 * Created by grigo on 5/10/17.
 */

@RestController
@RequestMapping("/artists/")
public class RestServices {

    private static final String template = "Hello, %s!";

    @Autowired
    private ICrudRepository<Artist, Integer> artistRepo;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping( method=RequestMethod.GET)
    public Artist[] getAll(){
        Collection<Artist> artists = artistRepo.getAll();
        return artists.toArray(new Artist[0]); // convert to array
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Integer id){

        Artist artist= artistRepo.find(id);
        if (artist==null)
            return new ResponseEntity<>("Artist not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Artist create(@RequestBody Artist artist){
        artistRepo.add(artist);
        return artist;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Artist update(@RequestBody Artist artist) {
        System.out.println("Updating user ...");
        artistRepo.add(artist);
        return artist;

    }


    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String artistError(RepositoryException e) {
        return e.getMessage();
    }
}