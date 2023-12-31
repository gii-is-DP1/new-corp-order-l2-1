/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package us.lsi.dp1.newcorporder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@SuperBuilder
@MappedSuperclass
@EqualsAndHashCode(of = "id")
public abstract class BaseEntity implements Model {

    @Id
    @SequenceGenerator(
        name = "entity_seq",
        sequenceName = "entity_sequence",
        initialValue = 100
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_seq")
    protected Integer id;

    public BaseEntity() {
    }

    public BaseEntity(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }
}
