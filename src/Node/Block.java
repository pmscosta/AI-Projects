package Node;

public class Block{

  public int x; 
  public int y; 
  public int type;

  public Block(int x, int y, int type) {
    this.x = x;
    this.y = y;
    this.type = type;
  }

  @Override
  public String toString(){
    return "Block: x:" + Integer.toString(this.x) + " y: " + Integer.toString(this.y) + " type: " + Integer.toString(this.type);
  }

  @Override
  public int hashCode() {
  	final int prime = 31;
  	int result = 1;
  	result = prime * result + type;
  	result = prime * result + x;
  	result = prime * result + y;
  	return result;
  }

  @Override
  public boolean equals(Object obj) {
  	if (this == obj)
  		return true;
  	if (obj == null)
  		return false;
  	if (getClass() != obj.getClass())
  		return false;
  	Block other = (Block) obj;
  	if (type != other.type)
  		return false;
  	if (x != other.x)
  		return false;
  	if (y != other.y)
  		return false;
  	return true;
  }
}