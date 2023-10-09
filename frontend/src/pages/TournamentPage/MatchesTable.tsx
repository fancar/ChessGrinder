import {MatchDto, MatchResult} from "lib/api/dto/TournamentPageData";

function MatchesTable(
    {
        matches,
        roundIsFinished,
        submitMatchResult,
    }: {
        matches: MatchDto[],
        roundIsFinished: boolean,
        submitMatchResult: (match: MatchDto, result: MatchResult | null) => void,
    }
) {
    return <div className={"grid grid-cols-12 p-2"}>
        <div className={"col-span-12 grid grid-cols-12 border-b border-black"}>
            <div className={"col-span-4 font-bold"}>White</div>
            <div className={"col-span-4 font-bold"}>Result</div>
            <div className={"col-span-4 font-bold"}>Black</div>
        </div>
        {
            matches.map((match, idx) => {
                return <div className={"col-span-12 grid grid-cols-12 border-b border-gray"} key={idx}>
                    <div className={"col-span-4"}>{match.white.name}</div>
                    <div className={"col-span-4"}>
                        <select defaultValue={match.result || ""}
                                disabled={roundIsFinished}
                                onChange={(e) => {
                                    submitMatchResult(match, e.target.value as MatchResult)
                                }}>
                            <option value={""}>Unknown</option>
                            <option value={"WHITE_WIN"}>White won</option>
                            <option value={"BLACK_WIN"}>Black won</option>
                            <option value={"DRAW"}>Draw</option>
                        </select>
                    </div>
                    <div className={"col-span-4"}>{match.black.name}</div>
                </div>
            })
        }
    </div>
}

export default MatchesTable
