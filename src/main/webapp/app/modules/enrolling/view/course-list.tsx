import React, { Component } from 'react';
import { connect } from 'react-redux';
import List from '@material-ui/core/List';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import SelectableCourseBlockRow from './selectable-course-block-row';
import SelectableCourseBlockDetails from '../../../shared/model/domain/dto/selectable-course-block-details';
import axios from 'axios';
import log from 'app/config/log';
import fakeData from '../fake-courses-data';
import CourseDetails from '../../../shared/model/domain/dto/course-details';


export type ICourseListProps = StateProps;


interface ICourseListState {
    courseBlocks: Array<SelectableCourseBlockDetails>
};

class CourseList extends Component<ICourseListProps, ICourseListState> {
    constructor(props: ICourseListProps) {
        super(props);

        this.state = {
            courseBlocks: []
        };
    }

    componentDidMount() {
        this.getData();
    };

    getData() {
        /* axios.get<Array<EnrollmentRightDetails>>("/api/enrollment-right")
        .then(r => {
          log.info(r.data);
          const data = r.data.map(element => mapEnrollmentRightDetailsToEnrollmentData(element));
          this.setState({ enrollments: data })
        })
        .catch(e => log.error(e))*/
        this.setState({ courseBlocks: fakeData })
    }

    renderHeader() {
        return (
            <Typography variant='h4' style={{textAlign: 'center'}}>
                Kursy
            </Typography>
        );
    }

    onCourseSelected(course: CourseDetails) {
        log.info('selected course');
        log.info(course);
    }

    renderCourseRows() {
        return (
            <>
                {this.state.courseBlocks.map(x => 
                    <SelectableCourseBlockRow key={x.id} selectableCourseBlock={x} onSelected={this.onCourseSelected} />
                )}
            </>
        );
    }

    render() {
        return (
            <>
                {this.renderHeader()}
                <Grid container spacing={2}>
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
