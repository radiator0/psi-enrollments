import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICourseUnit, defaultValue } from 'app/shared/model/course-unit.model';

export const ACTION_TYPES = {
  FETCH_COURSEUNIT_LIST: 'courseUnit/FETCH_COURSEUNIT_LIST',
  FETCH_COURSEUNIT: 'courseUnit/FETCH_COURSEUNIT',
  CREATE_COURSEUNIT: 'courseUnit/CREATE_COURSEUNIT',
  UPDATE_COURSEUNIT: 'courseUnit/UPDATE_COURSEUNIT',
  DELETE_COURSEUNIT: 'courseUnit/DELETE_COURSEUNIT',
  RESET: 'courseUnit/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICourseUnit>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CourseUnitState = Readonly<typeof initialState>;

// Reducer

export default (state: CourseUnitState = initialState, action): CourseUnitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COURSEUNIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COURSEUNIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COURSEUNIT):
    case REQUEST(ACTION_TYPES.UPDATE_COURSEUNIT):
    case REQUEST(ACTION_TYPES.DELETE_COURSEUNIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COURSEUNIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COURSEUNIT):
    case FAILURE(ACTION_TYPES.CREATE_COURSEUNIT):
    case FAILURE(ACTION_TYPES.UPDATE_COURSEUNIT):
    case FAILURE(ACTION_TYPES.DELETE_COURSEUNIT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COURSEUNIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COURSEUNIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COURSEUNIT):
    case SUCCESS(ACTION_TYPES.UPDATE_COURSEUNIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COURSEUNIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/course-units';

// Actions

export const getEntities: ICrudGetAllAction<ICourseUnit> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COURSEUNIT_LIST,
  payload: axios.get<ICourseUnit>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICourseUnit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COURSEUNIT,
    payload: axios.get<ICourseUnit>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICourseUnit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COURSEUNIT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICourseUnit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COURSEUNIT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICourseUnit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COURSEUNIT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
