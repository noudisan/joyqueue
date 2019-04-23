/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jd.journalq.broker.coordinator.assignment.converter;

import com.google.common.collect.Lists;
import com.jd.journalq.broker.coordinator.domain.PartitionAssignment;
import com.jd.journalq.domain.PartitionGroup;

import java.util.List;

/**
 * PartitionAssignmentConverter
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/6
 */
public class PartitionAssignmentConverter {

    public static PartitionAssignment convert(List<PartitionGroup> partitionGroupList) {
        List<Short> partitions = Lists.newLinkedList();
        for (PartitionGroup assignedPartitionGroup : partitionGroupList) {
            for (Short partition : assignedPartitionGroup.getPartitions()) {
                partitions.add(partition);
            }
        }
        PartitionAssignment partitionAssignment = new PartitionAssignment();
        partitionAssignment.setPartitions(partitions);
        return partitionAssignment;
    }
}