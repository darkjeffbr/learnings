export abstract class CrudRepository<K,E> {

    abstract count(): number;
    abstract deleteById(id: K): void;
    abstract existsById(id: K): boolean;
    abstract findAll(): E[];
    abstract findById(id: K): E;
    abstract save(entity: E): E;
    abstract update(id: K, entity: E): E;

}