import EnrollingGroupDetails from '../../../../shared/model/domain/dto/enrolling-group-details';
import GroupsData from '../../groups-data';

export default function mapEnrollingGroupDetailsToGroupsData(egd : EnrollingGroupDetails) {
    return new GroupsData(egd.id, egd.groupCode, egd.enrolledCount, egd.limit, egd.studentEnrolled, egd.canEnrollOverLimit,
        egd.schedules, egd.lecturerTitle, egd.lecturerFirstName, egd.lecturerSecondName, egd.lecturerLastName);
}