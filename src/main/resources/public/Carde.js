function Carde(x, y, word)
{
  if(x < 0 || x > 5 || y < 0 || y > 5)
    console.log("Invalide position");
  else
  {
    this.x = x;
    this.y = y;
    this.state = "DEFAULT";
    this.word = document.createElement("p");
    this.word.innerHTML = word;
    this.div = document.createElement("div");
    this.div.appendChild(this.word);
    this.div.id = y+(x*5);
    this.div.style.display = "inline-table";
    this.div.style.width = "18%";
    this.div.style.padding = "2%";
    this.div.style.marginLeft = "15px";
    this.div.style.marginTop = "15px";
    this.div.style.color = "#808080";
    this.div.style.fontWeight = "Bold";
    this.div.style.backgroundColor = "#F2F2F2";
    this.div.style.textAlign = "center";
    document.getElementById("cards").appendChild(this.div);
  }

  this.toSpymaster = function()
  {
      this.div.style.color = this.color;
      this.div.style.border = "1px solid" + this.color;
  }

  this.setColor = function(color)
  {
    this.color = color;
  }

  this.setName = function(name)
  {
    this.word.innerHTML = name;
  }

  this.flip = function(type)
  {
    if(this.state == "SELECTED")
      return;

    this.state = "SELECTED";
    this.div.style.color = "#FFFFFF";

    if(type == "RED")
      this.div.style.background = "#FF4D4D";

    if(type == "BLUE")
      this.div.style.background = "#66B3FF";

    if(type == "NEUTRAL")
      this.div.style.background = "#A6A6A6";

    if(type == "ASSASSIN")
      this.div.style.background = "#404040";

  }

  this.activateClick = function()
  {
    if(this.state === "DEFAULT") {
        this.div.onclick = function () {
            var tmp =parseInt(this.id);
           // var x = Math.floor(tmp / 5);
            var x = Math.floor(tmp/5);
            var y = tmp %5;

          var message = "MSGTYPE:SELECTCARD,"+x+","+y;

          console.log(message);
           socket.send(message);
        }
    }


  }

  this.desactivateClick = function()
  {
    this.div.onclick = function() {return false};
  }


}
