export default class CourseDetails {
  public readonly id: number;
  public readonly name: string;
  public readonly enrolled: boolean;

  constructor(id: number, name: string, enrolled: boolean) {
    this.id = id;
    this.name = name;
    this.enrolled = enrolled;
  }
};