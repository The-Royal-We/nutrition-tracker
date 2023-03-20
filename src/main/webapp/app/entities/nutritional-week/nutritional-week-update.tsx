import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INutritionalWeek } from 'app/shared/model/nutritional-week.model';
import { getEntity, updateEntity, createEntity, reset } from './nutritional-week.reducer';

export const NutritionalWeekUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nutritionalWeekEntity = useAppSelector(state => state.nutritionalWeek.entity);
  const loading = useAppSelector(state => state.nutritionalWeek.loading);
  const updating = useAppSelector(state => state.nutritionalWeek.updating);
  const updateSuccess = useAppSelector(state => state.nutritionalWeek.updateSuccess);

  const handleClose = () => {
    navigate('/nutritional-week');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...nutritionalWeekEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...nutritionalWeekEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="nutritionTrackerApp.nutritionalWeek.home.createOrEditLabel" data-cy="NutritionalWeekCreateUpdateHeading">
            <Translate contentKey="nutritionTrackerApp.nutritionalWeek.home.createOrEditLabel">Create or edit a NutritionalWeek</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="nutritional-week-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalWeek.dateFrom')}
                id="nutritional-week-dateFrom"
                name="dateFrom"
                data-cy="dateFrom"
                type="date"
              />
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalWeek.dateTo')}
                id="nutritional-week-dateTo"
                name="dateTo"
                data-cy="dateTo"
                type="date"
              />
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalWeek.name')}
                id="nutritional-week-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nutritional-week" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default NutritionalWeekUpdate;
