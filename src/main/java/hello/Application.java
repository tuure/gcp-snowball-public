package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
@RestController
public class Application {

    Integer mypositionx = 0;
    Boolean ruch = false;
    Integer mypositiony =0;
  Integer licznik =0;
  String href = "http://34.149.78.80.sslip.io";

  static class Self {
    public String href;
  }

  static class Links {
    public Self self;
  }

  static class PlayerState {
    public Integer x;
    public Integer y;
    public String direction;
    public Boolean wasHit;
    public Integer score;
  }

  static class Arena {
    public List<Integer> dims;
    public Map<String, PlayerState> state;
  }

  static class ArenaUpdate {
    public Links _links;
    public Arena arena;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.initDirectFieldAccess();
  }

  @GetMapping("/")
  public String index() {
    return "Let the battle begin!";
  }

  @PostMapping("/**")
  public String index(@RequestBody ArenaUpdate arenaUpdate) {
    System.out.println(arenaUpdate);
    String[] commands = new String[]{"F", "R", "L", "T"};
    int i = new Random().nextInt(4);
    PlayerState me = arenaUpdate.arena.state.getOrDefault(arenaUpdate._links.self.href, null);
    int max = 0;
    licznik++;
    if(me.wasHit && (!ruch || !(mypositiony == me.y && mypositionx == me.x))) {
        ruch = true;
        mypositionx = me.x;
        mypositiony = me.y;
        return "F";
    }
    ruch = false;
    if(licznik %2 ==0){
        System.out.println(me.x+ " "+me.y+ "L");
        if(licznik %6 ==0 || (mypositiony == me.y && mypositionx == me.x)) {
            mypositionx = me.x;
            mypositiony = me.y;
            return "L"; }

            else return "R";
    }
    else {
        mypositionx = -1;
        mypositiony = -1;
      System.out.println(me.x+ " "+me.y+ "T");
      if(hitSomeone(me, arenaUpdate)) {
          licznik++;
          return "T";
        } else return "F";
    }
  }

    private boolean hitSomeone(PlayerState me, ArenaUpdate arenaUpdate) {
        if(me.direction.equals("N"))
          if(someone(me.x,me.y-1,arenaUpdate) || someone(me.x,me.y-2,arenaUpdate) || someone(me.x,me.y-3,arenaUpdate)) return true;
          if(me.direction.equals("S"))
          if(someone(me.x,me.y+1,arenaUpdate) || someone(me.x,me.y+2,arenaUpdate) || someone(me.x,me.y+3,arenaUpdate)) return true;
          if(me.direction.equals("E"))
          if(someone(me.x+1,me.y,arenaUpdate) || someone(me.x+2,me.y,arenaUpdate) || someone(me.x+3,me.y,arenaUpdate)) return true;
          if(me.direction.equals("W"))
          if(someone(me.x-1,me.y,arenaUpdate) || someone(me.x-2,me.y,arenaUpdate) || someone(me.x-3,me.y,arenaUpdate)) return true;
        return false;
    }

    private boolean someone(Integer x, int i, ArenaUpdate arenaUpdate) {
        return arenaUpdate.arena.state.keySet().stream().anyMatch(key -> arenaUpdate.arena.state.get(key).x == x && arenaUpdate.arena.state.get(key).y == i );
    }

}

