export class Appointment {
    id?: number;
    title: string;
    notes: string[] = [];
    allDay: boolean = false;
    start?: Date;
    end?: Date;
    date: Date;

    constructor(title: string) {
        this.title = title;
        this.date = new Date();
    }
}