/*
 *
 *  Copyright 2017 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package com.netflix.hollow.core.type;

import com.netflix.hollow.api.custom.HollowAPI;
import com.netflix.hollow.api.custom.HollowObjectTypeAPI;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.type.delegate.DoubleDelegateLookupImpl;

public class DoubleTypeAPI extends HollowObjectTypeAPI {

    private final DoubleDelegateLookupImpl delegateLookupImpl;

    public DoubleTypeAPI(HollowAPI api, HollowObjectTypeDataAccess typeDataAccess) {
        super(api, typeDataAccess, new String[] {
            "value"
        });
        this.delegateLookupImpl = new DoubleDelegateLookupImpl(this);
    }

    public double getValue(int ordinal) {
        if(fieldIndex[0] == -1)
            return missingDataHandler().handleDouble("Double", ordinal, "value");
        return getTypeDataAccess().readDouble(ordinal, fieldIndex[0]);
    }

    public Double getValueBoxed(int ordinal) {
        double d;
        if(fieldIndex[0] == -1) {
            d = missingDataHandler().handleDouble("Double", ordinal, "value");
        } else {
            boxedFieldAccessSampler.recordFieldAccess(fieldIndex[0]);
            d = getTypeDataAccess().readDouble(ordinal, fieldIndex[0]);
        }
        return Double.isNaN(d) ? null : Double.valueOf(d);
    }

    public DoubleDelegateLookupImpl getDelegateLookupImpl() {
        return delegateLookupImpl;
    }

}