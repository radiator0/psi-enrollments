export default class EnrollmentUnit {
  public readonly id: number;
  public readonly startDate: Date;
  public readonly endDate: Date;

  constructor(id: number, startDate: Date, endDate: Date) {
    this.id = id;
    this.startDate = new Date(Date.parse(startDate.toString()));
    this.endDate = new Date(Date.parse(endDate.toString()));
  }
};