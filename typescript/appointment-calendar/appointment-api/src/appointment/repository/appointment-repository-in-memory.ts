import { Appointment } from "../model/appointment";
import { CrudRepository } from "./crud-repository";

export class AppointmentRepository extends CrudRepository<number, Appointment> {

    private _db: Map<number, Appointment> = new Map();

    constructor() {
        super();

        this.save({
            title: 'Medical appointment',
            notes: [],
            allDay: false,
            start: new Date(),
            end: new Date(),
            date: new Date()
        });
        
        this.save({
            title: 'Supermarket',
            notes: [
                'milk',
                'bread'
            ],
            allDay: false,
            start: new Date(),
            end: new Date(),
            date: new Date()
        });

    }

    count(): number {
        return this._db.size;
    }

    deleteById(id: number): void {
        this._db.delete(id)
    }

    existsById(id: number): boolean {
        return this._db.has(id);
    }

    findAll(): Appointment[] {
        return [...this._db.values()];
    }

    findById(id: number): Appointment {
        return this._db.get(id) as Appointment;
    }

    save(entity: Appointment): Appointment {
        if(!entity.id){
            entity.id = this._db.size+1;
        }else if( this.existsById(entity.id) ) {
            throw new Error('Entity with specified ID already exists');
        }
        this._db.set(entity.id, entity);
        return entity;
    }

    update(id: number, entity: Appointment): Appointment {
        this._db.set(id, entity);
        return entity;
    }

}