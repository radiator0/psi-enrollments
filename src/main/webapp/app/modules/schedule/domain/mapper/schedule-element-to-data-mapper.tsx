import ScheduleElement from '../../../../shared/model/domain/dto/schedule-element';
import ScheduleData from '../../schedule-data';

export default function mapScheduleElementToScheduleData(scheduleElement : ScheduleElement) {
    return new ScheduleData(scheduleElement.id, scheduleElement.startDate, scheduleElement.endDate, scheduleElement.courseShortName || scheduleElement.courseName,
        scheduleElement.classType.toString(), undefined, undefined,
        scheduleElement.lecturerTitle, scheduleElement.lecturerFirstName, scheduleElement.lecturerSecondName, scheduleElement.lecturerLastName,
        scheduleElement.room, scheduleElement.building);
}