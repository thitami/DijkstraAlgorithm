import java.util.List;

public interface IPath 
{
	public List<INode> getPath();
	public int getTotalWeight();
}