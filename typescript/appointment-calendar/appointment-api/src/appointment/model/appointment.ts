export interface Appointment {
    id?: number;
    title: string;
    notes: string[];
    allDay: boolean;
    start?: Date;
    end?: Date;
    date: Date;

    /*constructor(title: string) {
        this.title = title;
        this.date = new Date();
    }*/
}