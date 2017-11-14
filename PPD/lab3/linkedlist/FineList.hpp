//
// Created by nikitautiu on 11/6/17.
//

#ifndef LAB3_FINELIST_HPP
#define LAB3_FINELIST_HPP

#include "IList.hpp"

class FineList : public IList {
private:
    // node class
    class FineNode {
    private:
        float value;
        std::unique_ptr<FineNode> nextNode;
    public:
        std::mutex mtx;

        FineNode(float value, std::unique_ptr<FineNode> next) : value(value), nextNode(std::move(next)) {}

        FineNode(float value) : value(value), nextNode(nullptr) {}

        FineNode* getNextNode() const {
            return nextNode.get();
        }

        std::unique_ptr<FineNode> getNextPtr() {
            return std::move(nextNode);
        };

        void setNextNode(std::unique_ptr<FineNode> newNext) {
            nextNode = std::move(newNext);  // add the new next
        }

        float getValue() const {
            return value;
        }

    };

private:
    std::unique_ptr<FineNode> root;
    std::shared_mutex mtx; // instance lock. read/write lock
    friend class FineIterator;
public:
    FineList() : IList() {} // initialization should be default
    FineList(std::string fileName) : IList(fileName) {}  // with logging
    std::unique_ptr<IIterator> getIterator() override;
    std::mutex root_mtx;
private:
    // the hooks
    void insertImpl(float newElem) override;  // add new element
    void removeImpl(float elem) override;  // remove the said element
};

class FineIterator : public IIterator {
private:
    FineList::FineNode* currNode;
    FineList* list;
public:
    FineIterator(FineList& list);
    bool isValid() const override;
    void next() override;
    float getValue() override;
};

#endif //LAB3_FINELIST_HPP
