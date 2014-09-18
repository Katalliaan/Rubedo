package rubedo.raycast;

public interface IFilter<E> {
	boolean matches(E arg);
}
