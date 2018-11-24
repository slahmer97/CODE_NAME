var setName = function(x, y, name)
{
  carde[y+(x*5)].setName(name);
};

var setHint = function(hint)
{
  document.getElementById("hint").innerHTML = hint;
};
var endturn =function () {
    socket.send("MSGTYPE:SWITCH");
};
var sendmsg = function()
{
  var inp = document.getElementById("msg_txt");
  socket.send("MSGTYPE:MSG," + inp.value);
  inp.value = "";
};

var print_msg = function(type,msg)
{
    var p = document.createElement("p");
    p.innerHTML = msg;

    if(type === "CHATMSGF")
        p.classList.add("rcv");

    else if(type === "CHATMSGP")
        p.classList.add("snd");

    var div = document.getElementById("msg_rcv");
    div.appendChild(p);
    div.scrollTop = div.scrollHeight;
};

var print_game_id = function(id)
{
    var p = document.createElement("p");
    p.innerHTML = "<b>Game id: </b>" + id;
    p.classList.add("game_id");

    var pp = document.createElement("p");
    pp.innerHTML = "<b>Game id: </b>" + id;
    pp.classList.add("game_id");
    document.querySelector("#game_id").appendChild(pp);
    document.querySelector("#game_id_2").appendChild(p);
};

var end_game = function(msg)
{
    document.getElementById("game_id_2").innerHTML ="";
    if(msg === "WIN")
        document.getElementById("end_game").innerHTML = "<b>YOU WON !!!<b>";
    if(msg === "LOST")
        document.getElementById("end_game").innerHTML = "<b>YOU LOST!!!<b>";

    document.getElementById('modal-container').classList.add('visible');
};

var end_wait = function()
{
    document.getElementById('modal-container').classList.remove('visible');
};
var changeToSpymaster = function()
{
    var ht = document.getElementById("hint");
    var endt = document.getElementById("end_turn");
    var id = document.getElementById("game_id");
    var haut = document.getElementById("haut");
    haut.removeChild(ht);
    var div = document.createElement("div");
    div.classList.add("hint_in");
    var inp1 = document.createElement("input");
    inp1.id = "ht";
    inp1.type = "text";
    inp1.placeholder = "HINT";
    div.appendChild(inp1);
    var inp2 = document.createElement("input");
    inp2.id = "nb";
    inp2.type = "number";
    inp2.min = "1";
    inp2.max = "9";
    inp2.placeholder = "NB";
    div.appendChild(inp2);
    var bt = document.createElement("input");
    bt.type = "button";
    bt.name = "send_hint";
    bt.value = "OK";
    div.appendChild(bt);
    document.body.removeChild(endt);
    document.body.insertBefore(div, id);
};

var setScore = function(board)
{
  var bs = 0, rs = 0;
  for(var i=0; i<5; i++)
    for(var j=0; j<5; j++)
    {
      if(board[i][j].state === "DEFAULT" && board[i][j].type === "BLUE")
        bs++;

      if(board[i][j].state === "DEFAULT" && board[i][j].type === "RED")
        rs++;
    }
  document.getElementById("score").innerHTML = bs + " - " + rs;
};

var initSpy = function(words, board)
{
  changeToSpymaster();
  for(var i=0; i<5; i++)
    for(var j=0; j<5; j++)
    {
        if (board[i][j].type === "RED")
            carde[j + (i * 5)].setColor("#FF4D4D");

        if (board[i][j].type === "BLUE")
            carde[j + (i * 5)].setColor("#66B3FF");

        if (board[i][j].type === "NEUTRAL")
            carde[j + (i * 5)].setColor("#A6A6A6");

        if (board[i][j].type === "ASSASSIN")
            carde[j + (i * 5)].setColor("#404040");

        carde[j + (i * 5)].toSpymaster();
        setName(i, j, words.getWord(board[i][j].id));
    }
};

var initPlayer = function(words, board)
{
  for(var i=0; i<5; i++)
    for(var j=0; j<5; j++)
      setName(i, j, words.getWord(board[i][j].id));

};

var initView = function(view, board)
{
  var words = new Words();
  if(view === "SPYMASTER")
    initSpy(words, board);
  else
    initPlayer(words, board);
};

var updateView = function(board)
{
  for(var i=0; i<5; i++)
    for(var j=0; j<5; j++)
      if(carde[j+(i*5)].state !== board[i][j].state)
        carde[j+(i*5)].flip(board[i][j].type);
};
var activateView = function(view)
{
    if(view === 1)
    {
        document.getElementsByName("send_hint")[0].onclick = function()
        {
            var ht = document.getElementById("ht").value;
            var nb = document.getElementById("nb").value;
            if(nb === "" || ht === "")
                alert("Must indicat a number");
            else
                socket.send("MSGTYPE:HINT,clue:" + ht + ",num:" + nb);
        }
    }
    else
    {
        for (var x = 0; x < 25; x++)
            carde[x].activateClick();
    }
};
var desactivateView = function(view)
{
  if(view === 1)
    document.getElementsByName("send_hint")[0].onclick = function() { return false;}
  else {
      for (var x = 0; x < 25; x++)
          carde[x].desactivateClick();
  }
};


var carde = [
  new Carde(0, 0, "Codename"),
  new Carde(0, 1, "Codename"),
  new Carde(0, 2, "Codename"),
  new Carde(0, 3, "Codename"),
  new Carde(0, 4, "Codename"),
  new Carde(1, 0, "Codename"),
  new Carde(1, 1, "Codename"),
  new Carde(1, 2, "Codename"),
  new Carde(1, 3, "Codename"),
  new Carde(1, 4, "Codename"),
  new Carde(2, 0, "Codename"),
  new Carde(2, 1, "Codename"),
  new Carde(2, 2, "Codename"),
  new Carde(2, 3, "Codename"),
  new Carde(2, 4, "Codename"),
  new Carde(3, 0, "Codename"),
  new Carde(3, 1, "Codename"),
  new Carde(3, 2, "Codename"),
  new Carde(3, 3, "Codename"),
  new Carde(3, 4, "Codename"),
  new Carde(4, 0, "Codename"),
  new Carde(4, 1, "Codename"),
  new Carde(4, 2, "Codename"),
  new Carde(4, 3, "Codename"),
  new Carde(4, 4, "Codename"),
];

var b =
[[{"id":0,"state":"DEFAULT","type":"RED"},{"id":1,"state":"DEFAULT","type":"RED"},{"id":2,"state":"DEFAULT","type":"RED"},{"id":3,"state":"DEFAULT","type":"RED"},{"id":4,"state":"DEFAULT","type":"RED"}],
[{"id":5,"state":"DEFAULT","type":"RED"},{"id":6,"state":"DEFAULT","type":"RED"},{"id":7,"state":"DEFAULT","type":"RED"},{"id":8,"state":"DEFAULT","type":"NEUTRAL"},{"id":9,"state":"DEFAULT","type":"BLUE"}],
[{"id":10,"state":"DEFAULT","type":"BLUE"},{"id":11,"state":"DEFAULT","type":"BLUE"},{"id":12,"state":"DEFAULT","type":"BLUE"},{"id":13,"state":"DEFAULT","type":"BLUE"},{"id":14,"state":"DEFAULT","type":"BLUE"}],
[{"id":15,"state":"DEFAULT","type":"BLUE"},{"id":16,"state":"DEFAULT","type":"BLUE"},{"id":17,"state":"DEFAULT","type":"BLUE"},{"id":18,"state":"DEFAULT","type":"ASSASSIN"},{"id":19,"state":"DEFAULT","type":"NEUTRAL"}],
[{"id":20,"state":"DEFAULT","type":"NEUTRAL"},{"id":21,"state":"DEFAULT","type":"NEUTRAL"},{"id":22,"state":"DEFAULT","type":"NEUTRAL"},{"id":23,"state":"DEFAULT","type":"NEUTRAL"},{"id":24,"state":"DEFAULT","type":"NEUTRAL"}]];
