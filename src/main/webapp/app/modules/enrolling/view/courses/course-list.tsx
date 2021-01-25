import React, { Component } from 'react';
import { connect } from 'react-redux';
import List from '@material-ui/core/List';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import SelectableCourseBlockRow from './selectable-course-block-row';
import SelectableCourseBlockDetails from '../../../../shared/model/domain/dto/selectable-course-block-details';
import log from 'app/config/log';
import CourseDetails from '../../../../shared/model/domain/dto/course-details';
import CoursesData from '../../courses-data';
import { Translate } from 'react-jhipster';

export type ICourseListProps = {
    coursesData: Array<CoursesData>,
    onSelected: (course: CourseDetails) => void
};;


// eslint-disable-next-line @typescript-eslint/no-empty-interface
interface ICourseListState {
};

class CourseList extends Component<ICourseListProps, ICourseListState> {
    constructor(props: ICourseListProps) {
        super(props);

        this.state = {
            coursesData: []
        };
    }

    componentDidMount() {
    };

    renderHeader() {
        return (
            <Typography variant='h4' style={{textAlign: 'center'}}>
                <Translate contentKey={'enrolling.header.courses'}>Courses</Translate>
            </Typography>
        );
    }

    renderCourseRows() {
        const { coursesData, onSelected } = this.props;
        return (
            <>
                {coursesData?.map((x, id) => 
                    <SelectableCourseBlockRow key={id} selectableCourseBlock={x.selectableCourseBlocks} onSelected={onSelected} />
                )}
            </>
        );
    }

    render() {
        return (
            <>
                {this.renderHeader()}
                <Grid container spacing={1}>
                    <List style={{flexDirection: 'row', width:'100%', flex:1}}>
                        {this.renderCourseRows()}
                    </List>
                </Grid>
            </>
        );
    }
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(CourseList);
