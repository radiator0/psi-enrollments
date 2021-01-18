import ScheduleElement from './schedule-element';
import ScheduleData from './schedule-data';
import GroupState from './group-state';

export default function mapScheduleElementToScheduleData(scheduleElement : ScheduleElement) {
    return new ScheduleData(scheduleElement.id, scheduleElement.startDate, scheduleElement.endDate,
        scheduleElement.courseShortName, scheduleElement.classType.toString(),
        scheduleElement.lecturerTitle, scheduleElement.lecturerFirstName, scheduleElement.lecturerSecondName, scheduleElement.lecturerLastName,
        scheduleElement.room, scheduleElement.building,
        GroupState.Irrelevant);
}