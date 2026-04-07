# MongoDB Schema for BidStream

## Collections

### users
- `_id`: ObjectId
- `username`: string, unique
- `email`: string, unique
- `passwordHash`: string
- `balance`: decimal128
- `active`: boolean

### auctionItems
- `_id`: ObjectId
- `title`: string
- `description`: string
- `startingPrice`: decimal128
- `currentPrice`: decimal128
- `currentWinnerId`: string
- `endsAt`: date
- `live`: boolean

### bids
- `_id`: ObjectId
- `auctionItemId`: string
- `bidderId`: string
- `amount`: decimal128
- `placedAt`: date

## Sample index setup

```js
db.users.createIndex({ username: 1 }, { unique: true });
db.users.createIndex({ email: 1 }, { unique: true });
db.auctionItems.createIndex({ live: 1, endsAt: 1 });
db.bids.createIndex({ auctionItemId: 1, placedAt: -1 });
```

## Notes

- Use `decimal128` for precise monetary values.
- Ensure `currentPrice` is always updated atomically with each accepted bid.
- Store bid history in `bids` for audit, and query the latest bid for real-time updates.
